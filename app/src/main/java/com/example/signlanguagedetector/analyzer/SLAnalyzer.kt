package com.example.signlanguagedetector.analyzer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.YuvImage
import android.media.Image
import android.os.SystemClock
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.core.graphics.scale
import com.example.signlanguagedetector.ml.MyModel2
import com.example.signlanguagedetector.utilities.HandLandmark
import com.example.signlanguagedetector.utilities.OverlayView
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.invoke
import kotlinx.coroutines.runBlocking
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.Tensor
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import org.tensorflow.lite.task.core.vision.ImageProcessingOptions
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.nio.FloatBuffer


//created with tflite model
class SLAnalyzer(
    private val context: Context,
    private val onResult: (String) -> Unit
) : ImageAnalysis.Analyzer {

    val model = MyModel2.newInstance(context)

    val interpreter = Interpreter(
        FileUtil.loadMappedFile(context, "my_model2.tflite")
    )

    val handLandmark = HandLandmark(context)
    val overlayView = OverlayView(context, null)

    val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    init {
        interpreter.allocateTensors()
    }

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(image: ImageProxy) {

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
        val shape = handLandmark.detect3(image)
        if(shape.isNullOrEmpty()) {
            println("CUPCAKE: IS EMPTY with shape: ${shape?.size}")
            image.close()
            return
        }

        val tensor = TensorBuffer.createFixedSize(intArrayOf(1, 64, 21, 3), DataType.FLOAT32)
        tensor.loadBuffer(floatArrayToByteBuffer(shape))
//        tensor.loadBuffer(floatArrayToByteBuffer(shape.normalize()))

        val processedOutput = model.process(tensor)
        val outputArray = processedOutput.outputFeature0AsTensorBuffer.floatArray

        val max = outputArray.maxOrNull()
        onResult(alphabet[outputArray.asList().indexOf(max)].toString())
        Thread().run {
            Thread.sleep(1000)
            image.close()
        }
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