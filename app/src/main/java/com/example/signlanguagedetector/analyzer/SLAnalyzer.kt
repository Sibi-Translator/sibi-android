package com.example.signlanguagedetector.analyzer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.YuvImage
import android.media.Image
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.compose.runtime.mutableStateOf
import com.chaquo.python.Python
import com.example.signlanguagedetector.ml.MyModel2
import com.example.signlanguagedetector.utilities.FaceLandmark
import com.example.signlanguagedetector.utilities.HandLandmark
import com.example.signlanguagedetector.utilities.OverlayView
import com.example.signlanguagedetector.utilities.PoseLandmark
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.tasks.components.containers.NormalizedLandmark
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer


//created with tflite model
class SLAnalyzer(
    private val context: Context,
    private val onResult: (String) -> Unit
) : ImageAnalysis.Analyzer {

    val model = MyModel2.newInstance(context)

    var isPaused = false

    val interpreter = Interpreter(
        FileUtil.loadMappedFile(context, "my_model2.tflite")
    )

    val handLandmark = HandLandmark(context)
    val faceLandmark = FaceLandmark(context)
    val poseLandmark = PoseLandmark(context)
    val overlayView = OverlayView(context, null)

    val python = Python.getInstance()
    val module = python.getModule("chaquopy")
    val dataProcessor = module["processData"]
    val array = module["arr_frame"]

    val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    val mutableList = mutableListOf<Array<Array<FloatArray>>>()

    val emptyHandLandmark = mutableListOf<NormalizedLandmark>()
    val emptyPoseLandmark = mutableListOf<NormalizedLandmark>()
    val emptyFaceLandmark = mutableListOf<NormalizedLandmark>()

    init {
        interpreter.allocateTensors()
        var arr = NormalizedLandmark.create(0f, 0f, 0f)
        for(i in 0 until 22) {
            emptyHandLandmark.add(arr)
            if(i < 12)
                emptyFaceLandmark.add(arr)
            if(i<6)
                emptyPoseLandmark.add(arr)
        }
//        println("Cupcake :" + module)
//        val test = module["test"]
//        println("Cupcake :" + test?.call()?.toInt())
    }

    var n = 0

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(image: ImageProxy) {
        if(isPaused) {
            return image.close()
        }
        try {
            if(n > 9) {
                val x = predict()
                n = 0
                onResult(x.toString())
            } else {
                val mpImage = BitmapImageBuilder(image.toBitmap()).build()
                val hands = handLandmark.detect(mpImage) ?: return image.close()
                var leftArray = mutableListOf<NormalizedLandmark>()
                var rightArray = mutableListOf<NormalizedLandmark>()
                hands.handednesses().forEachIndexed { index, it ->
                    try {
                        if(it.first().displayName() == "Left") {
                            leftArray.addAll(hands.landmarks().get(index))
                        } else {
                            rightArray.addAll(hands.landmarks().get(index))
                        }
                    } catch (e: Exception) {

                    }
                }
                if(leftArray.isEmpty())
                    leftArray = emptyHandLandmark
                if(rightArray.isEmpty())
                    rightArray = emptyHandLandmark
                leftArray.removeAt(1)
                rightArray.removeAt(1)
                val la = leftArray.map { arrayOf(it.x(), it.y(), it.z()) }.toTypedArray()
                val ra = rightArray.map { arrayOf(it.x(), it.y(), it.z()) }.toTypedArray()
                val face = faceLandmark.detect(mpImage)
                val faceArray = face.map {
                    arrayOf(it.x(), it.y(), it.z())
                }.toTypedArray()
                val pose = poseLandmark.detect(mpImage)
                val poseArray = pose.first().slice(11..16).map {
                    arrayOf(it.x(), it.y(), it.z())
                }.toTypedArray()

                val result = dataProcessor?.call(poseArray, la, ra, faceArray)
                n += 1;
            }
            image.close()
        } catch (e: Exception) {
            image.close()
        }

        //v1 - with 64 frame reading
//        var shape = handLandmark.detect2(image)
//        if(shape.isNullOrEmpty()) {
//            if(shape == null) {
//                image.close()
//                return
//            }
//            println("CUPCAKE: IS EMPTY with shape: ${shape.size}")
//            runBlocking {
//                delay(500)
//            }
//            image.close()
//            return
//        }
//        shape = shape.normalize()
//
//        val tensor = TensorBuffer.createFixedSize(intArrayOf(1, 64, 21, 3), DataType.FLOAT32)
//        tensor.loadBuffer(floatArrayToByteBuffer(shape))
//        println(tensor.shape.joinToString(separator = ","))
//
//        val processedOutput = model.process(tensor)
//        println("CUPCAKE: " + processedOutput.outputFeature0AsTensorBuffer.shape.joinToString(separator = ","))
//        val array = processedOutput.outputFeature0AsTensorBuffer.floatArray
//        println("CUPCAKE: " + array.joinToString(separator = ","))
//        val max = array.max()
//        onResult(alphabet[array.asList().indexOf(max)].toString())
//        println("CUPCAKE: " + alphabet[array.asList().indexOf(max)])
//        runBlocking {
//            delay(1000)
//            image.close()
//        }

        //v2
//        val shape = handLandmark.detect3(image)
//        if(shape.isNullOrEmpty()) {
//            println("CUPCAKE: IS EMPTY with shape: ${shape?.size}")
//            image.close()
//            return
//        }
//
//        val tensor = TensorBuffer.createFixedSize(intArrayOf(1, 64, 21, 3), DataType.FLOAT32)
//        tensor.loadBuffer(floatArrayToByteBuffer(shape))
////        tensor.loadBuffer(floatArrayToByteBuffer(shape.normalize()))
//
//        val processedOutput = model.process(tensor)
//        val outputArray = processedOutput.outputFeature0AsTensorBuffer.floatArray
//
//        val max = outputArray.maxOrNull()
//        onResult(alphabet[outputArray.asList().indexOf(max)].toString())
//        Thread().run {
//            Thread.sleep(1000)
//            image.close()
//        }
    }

    fun predict(): String? {
        val predict = module["predict"]
        val result = predict?.call()
        println("Cupcake: " + result)
        return result?.toString()
    }

    private fun toBitmap(image: Image): Bitmap {
        val planes = image.planes
        val yBuffer = planes[0].buffer
        val uBuffer = planes[1].buffer
        val vBuffer = planes[2].buffer

        val ySize = yBuffer.position()
        val uSize = uBuffer.position()
        val vSize = vBuffer.position()

        val nv21 = ByteArray(ySize + uSize + vSize)
        //U and V are swapped
        yBuffer[nv21, 0, ySize]
        vBuffer[nv21, ySize, vSize]
        uBuffer[nv21, ySize + vSize, uSize]

        val yuvImage = YuvImage(nv21, ImageFormat.NV21, image.width, image.height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 30, out)

        val imageBytes = out.toByteArray()
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size, BitmapFactory.Options())
    }

    fun floatArrayToByteBuffer(floatArrays: Array<Array<Array<FloatArray>>>): ByteBuffer {
        val totalElements = floatArrays.flatMap { array1 ->
            array1.flatMap { array2 ->
                array2.flatMap { floatArray ->
                    floatArray.asList()
                }
            }
        }.size

        val byteBuffer = ByteBuffer.allocate(totalElements * 4)
        val floatBuffer = byteBuffer.asFloatBuffer()

        for (array1 in floatArrays) {
            for (array2 in array1) {
                for (floatArray in array2) {
                    floatBuffer.put(floatArray)
                }
            }
        }

        byteBuffer.rewind()
        return byteBuffer
    }

    fun Array<Array<Array<FloatArray>>>.normalize(): Array<Array<Array<FloatArray>>> {
        val arr = this
        for(i in 0 until 21) {
            for (j in 0 until 3) {
                val list = mutableListOf<Float>()
                for (k in 0 until 64) {
                    list.add(arr[0][k][i][j])
                }
                val max = list.max()
                val min = list.min()

                if(min == max) {
                    for (k in 0 until 64) {
                        arr[0][k][i][j] = 0f
                    }
                } else {
                    for (k in 0 until 64) {
                        arr[0][k][i][j] = (arr[0][k][i][j] - min) / (max - min)
                    }
                }
            }
        }
        return arr
    }

}