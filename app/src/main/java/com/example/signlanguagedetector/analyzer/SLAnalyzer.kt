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
import com.example.signlanguagedetector.ml.Model
import com.example.signlanguagedetector.utilities.FaceLandmark
import com.example.signlanguagedetector.utilities.HandLandmark
import com.example.signlanguagedetector.utilities.OverlayView
import com.example.signlanguagedetector.utilities.PoseLandmark
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.tasks.components.containers.NormalizedLandmark
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer


//created with tflite model
class SLAnalyzer(
    private val context: Context,
    private val onResult: (String) -> Unit
) : ImageAnalysis.Analyzer {

    val model = Model.newInstance(context)

    var isPaused = true

    val interpreter = Interpreter(
        FileUtil.loadMappedFile(context, "model.tflite")
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
        for(i in 0 until 21) {
            emptyHandLandmark.add(arr)
            if(i < 12)
                emptyFaceLandmark.add(arr)
            if(i<6)
                emptyPoseLandmark.add(arr)
        }
    }

    var n = 0



    @OptIn(ExperimentalGetImage::class)
    override fun analyze(image: ImageProxy) {
        try {
            if(!isPaused)  {
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
                if(leftArray.isEmpty()) {
                    leftArray.clear()
                    leftArray.addAll(emptyHandLandmark)
                }
                if(rightArray.isEmpty()) {
                    rightArray.clear()
                    rightArray.addAll(emptyHandLandmark)
                }
                leftArray.removeAt(1)
                rightArray.removeAt(1)
                val la = leftArray.map { arrayOf(it.x(), it.y(), it.z()) }.toTypedArray()
                val ra = rightArray.map { arrayOf(it.x(), it.y(), it.z()) }.toTypedArray()
                var face = faceLandmark.detect(mpImage)
                if(face == null || face.isEmpty()) {
                    face = emptyFaceLandmark
                }
                val faceArray = face.map {
                    arrayOf(it.x(), it.y(), it.z())
                }.toTypedArray()
                var pose = poseLandmark.detect(mpImage)
                if(pose == null || pose.isEmpty()) {
                    pose = emptyPoseLandmark
                }
                val poseArray = pose.map {
                    arrayOf(it.x(), it.y(), it.z())
                }.toTypedArray()

                dataProcessor?.call(poseArray, la, ra, faceArray)
            }
            image.close()
        } catch (e: Exception) {
            e.printStackTrace()
            image.close()
        }
    }

    fun predict(): String {
        val size = module["getSize"]?.call()?.toInt()
        if(size == null || size == 0) {
            return ""
        }
        val predict = module["predict"]

        try {
            val result = predict?.call()
            onResult(result.toString())
            return result.toString()
        } catch (e : Exception) {
            e.printStackTrace()
        }

        return ""
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

    fun floatArrayToByteBuffer(floatArrays: Array<Array<Array<Float>>>): ByteBuffer {
        val totalElements = floatArrays.flatMap { array1 ->
            array1.flatMap { array2 ->
                array2.asList()
            }
        }.size

        val byteBuffer = ByteBuffer.allocate(totalElements * 4)
        val floatBuffer = byteBuffer.asFloatBuffer()

        for (array1 in floatArrays) {
            for (array2 in array1) {
                for (item in array2) {
                    floatBuffer.put(item)
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