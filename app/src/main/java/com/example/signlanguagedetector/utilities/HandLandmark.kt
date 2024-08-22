package com.example.signlanguagedetector.utilities

import android.content.Context
import androidx.camera.core.ImageProxy
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarker
import kotlin.math.pow

class HandLandmark(
    private val context: Context
) {

    val baseOptionsBuilder = BaseOptions.builder().setModelAssetPath("hand_landmarker.task")
    val baseOptions = baseOptionsBuilder.build()

    val optionsBuilder =
        HandLandmarker.HandLandmarkerOptions.builder()
            .setBaseOptions(baseOptions)
            .setMinHandDetectionConfidence(0.5f)
            .setMinTrackingConfidence(0.5f)
            .setMinHandPresenceConfidence(0.5f)
            .setNumHands(2)
//            .setResultListener({ result, input ->
//                println("one frame")
//                println(result)
//                println(input)
//            })
    val options = optionsBuilder.build()

    var handLandmarker = HandLandmarker.createFromOptions(
        context,
        options
    )

    fun detect(image: ImageProxy) : Array<Array<Array<FloatArray>>> {
        val mpImage = BitmapImageBuilder(image.toBitmap())
        val x = handLandmarker.detect(mpImage.build())
        if(x.landmarks().isEmpty()) {
            println("CUPCAKE: empty landmark")
            return arrayOf();
        }
        var hx = x.landmarks().first().toList().map {
            it.x()
        }
        var hy = x.landmarks().first().toList().map {
            it.y()
        }
        var hz = x.landmarks().first().toList().map {
            it.z()
        }

        var stdX = standardDeviationUsingMathPackage(x.landmarks().first().toList().map {
            it.x()
        })
        var stdY = standardDeviationUsingMathPackage(x.landmarks().first().toList().map {
            it.y()
        })
        val stdZ = standardDeviationUsingMathPackage(x.landmarks().first().toList().map {
            it.z()
        })

        if(stdZ != 0f) {
            hz = hz.map {
                it/stdZ
            }
        }
        val jx = hx.max() - hx.min()
        val jy = hy.max() - hy.min()

        var ssx = listOf<Float>()
        var ssy = listOf<Float>()
        if(jx > jy) {
            ssx = hx.map {
                it - hx.min()/jx
            }
            ssy = hy.map {
                it - hx.min()/jx
            }
        } else {
            ssx = hx.map {
                it - hy.min()/jy
            }
            ssy = hy.map {
                it - hy.min()/jy
            }
        }

        var arrFrame = arrayOf<FloatArray>()
        ssx.forEachIndexed { index, fl ->
            arrFrame += floatArrayOf(fl, ssy[index], hz[index])
        }

        return reshapeToModelInput(ssx.toFloatArray(), ssy.toFloatArray(), hz.toFloatArray());
    }

    fun detect2(image: ImageProxy) : Array<FloatArray> {
        val mpImage = BitmapImageBuilder(image.toBitmap())
        val x = handLandmarker.detect(mpImage.build())
        if(x.landmarks().isEmpty()) {
            println("CUPCAKE: empty landmark")
            return arrayOf();
        }
        var hx = x.landmarks().first().toList().map {
            it.x()
        }
        var hy = x.landmarks().first().toList().map {
            it.y()
        }
        var hz = x.landmarks().first().toList().map {
            it.z()
        }

        var stdX = standardDeviationUsingMathPackage(x.landmarks().first().toList().map {
            it.x()
        })
        var stdY = standardDeviationUsingMathPackage(x.landmarks().first().toList().map {
            it.y()
        })
        val stdZ = standardDeviationUsingMathPackage(x.landmarks().first().toList().map {
            it.z()
        })

        if(stdZ != 0f) {
            hz = hz.map {
                it/stdZ
            }
        }
        val jx = hx.max() - hx.min()
        val jy = hy.max() - hy.min()

        var ssx = listOf<Float>()
        var ssy = listOf<Float>()
        if(jx > jy) {
            ssx = hx.map {
                it - hx.min()/jx
            }
            ssy = hy.map {
                it - hx.min()/jx
            }
        } else {
            ssx = hx.map {
                it - hy.min()/jy
            }
            ssy = hy.map {
                it - hy.min()/jy
            }
        }

        var arrFrame = arrayOf<FloatArray>()
        ssx.forEachIndexed { index, fl ->
            arrFrame += floatArrayOf(fl, ssy[index], hz[index])
        }

//        return reshapeToModelInput(ssx.toFloatArray(), ssy.toFloatArray(), hz.toFloatArray());
        return reshapeToModelInput2(ssx.toFloatArray(), ssy.toFloatArray(), hz.toFloatArray());
        // Reshape for model input
//        val xTrain = arrFrame.reshape(-1, splitFrame, 21, 3)
//
//        // Prediction
//        val yPredProbabilities = model.predict(xTrain)
//        val yPred = yPredProbabilities.argmax(axis = 1)[0]
//        val predictedGesture = da[yPred]
//
//        println("\rTerprediksi: $predictedGesture")
//        arrFrame.clear()
//        predictS += " $predictedGesture"
//
//        if (predictS.length == 10) {
//            predictS = predictS.substring(2)
//        }

    }

    fun standardDeviationUsingMathPackage(numbers: List<Float>): Float {
        val mean = numbers.average()
        val variance = numbers.map { (it - mean).pow(2) }.average()
        return Math.sqrt(variance).toFloat()
    }

    private fun reshapeToModelInput(x: FloatArray, y: FloatArray, z: FloatArray, shape: IntArray = intArrayOf(1, 64, 21, 3)): Array<Array<Array<FloatArray>>> {
        val reshaped = Array(shape[0]) { Array(shape[1]) { Array(shape[2]) { FloatArray(shape[3]) } } }
        var index = 0
        for (i in 0 until shape[0]) {
            for (j in 0 until shape[1]) {
                for (k in 0 until shape[2]) {
                    for (l in 0 until shape[3]) {
                        when {
                            index < x.size -> reshaped[i][j][k][l] = x[index]
                            index < x.size + y.size -> reshaped[i][j][k][l] = y[index - x.size]
                            else -> reshaped[i][j][k][l] = z[index - x.size - y.size]
                        }
                        index++
                        if (index >= x.size + y.size + z.size) {
                            index = 0 // Reset index to repeat data
                        }
                    }
                }
            }
        }
        return reshaped
    }

    private fun reshapeToModelInput2(x: FloatArray, y: FloatArray, z: FloatArray): Array<FloatArray> {
        val reshaped = Array(21) { FloatArray(3) }
        var index = 0
        for (i in 0 until 21) {
            for (j in 0 until 3) {
                reshaped[i][j] = when (index) {
                    in x.indices -> x[index]
                    in x.size until x.size + y.size -> y[index - x.size]
                    else -> z[index - x.size - y.size]
                }
                index++
            }
        }
        return reshaped
    }
}