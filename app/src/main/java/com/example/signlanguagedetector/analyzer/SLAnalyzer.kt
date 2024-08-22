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
import kotlinx.coroutines.delay
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
        val shape = handLandmark.detect(image)
        if(shape.isEmpty()) {
            println("CUPCAKE: IS EMPTY with shape: ${shape.size}")
            runBlocking {
                delay(500)
            }
            image.close()
            return;
        }

        //v1 - with exact copy as 64 frames
        val tensor = TensorBuffer.createFixedSize(intArrayOf(1, 64, 21, 3), DataType.FLOAT32)

        tensor.loadBuffer(floatArrayToByteBuffer(shape))

        val processedOutput = model.process(tensor)
        val array = processedOutput.outputFeature0AsTensorBuffer.floatArray
        val max = array.max()
        println("CUPCAKE: " + alphabet[array.asList().indexOf(max)])

        //v2
//        if(addNewData(shape) == 1) {
//            val tensor = TensorBuffer.createFixedSize(intArrayOf(1, 64, 21, 3), DataType.FLOAT32)
//
//            tensor.loadBuffer(dataBuffer)
//
//            val processedOutput = model.process(tensor)
//            val array = processedOutput.outputFeature0AsTensorBuffer.floatArray
//            val max = array.max()
//            dataBuffer.clear()
//            println("CUPCAKE: " + alphabet[array.asList().indexOf(max)])
//        } else {
//            println("Progress: " +index.toString() + "/" + dataBuffer.capacity())
//        }

        // need to run
//        runBlocking {
//            delay(50)
//        }
        image.close()
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

    var dataBuffer : ByteBuffer = ByteBuffer.allocate(4032 * 4)
    var floatBuffer : FloatBuffer = dataBuffer.asFloatBuffer()
    var index = 0;

    fun addNewData(data: Array<FloatArray>): Int {
        val totalElements = data.flatMap { floatArray ->
            floatArray.asList()
        }.size

        // Copy the new data to the FloatBuffer
        for (floatArray in data) {
            floatBuffer.put(floatArray, 0, floatArray.size)
        }

        index += totalElements

        // Return 1 if full, else return 0
        return if (index == dataBuffer.capacity()) 1 else 0
    }
}