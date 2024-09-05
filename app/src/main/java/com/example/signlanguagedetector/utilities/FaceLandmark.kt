package com.example.signlanguagedetector.utilities

import android.content.Context
import androidx.camera.core.ImageProxy
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.framework.image.MPImage
import com.google.mediapipe.tasks.components.containers.NormalizedLandmark
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.facelandmarker.FaceLandmarker


class FaceLandmark(
    private val context: Context
) {

    val baseOptionBuilder = BaseOptions.builder().setModelAssetPath("face_landmarker.task")
    val baseOptions = baseOptionBuilder.build()

    val optionBuilder =
        FaceLandmarker.FaceLandmarkerOptions.builder()
            .setBaseOptions(baseOptions)
            .setNumFaces(1)
            .setMinFacePresenceConfidence(0.5f)
            .setMinTrackingConfidence(0.5f)
            .setMinFacePresenceConfidence(0.5f)
            .setRunningMode(RunningMode.IMAGE)
    val options = optionBuilder.build()

    val faceLandmarker = FaceLandmarker.createFromOptions(context, options)

    fun detect(image: MPImage) : MutableList<NormalizedLandmark> {
        val x = faceLandmarker.detect(image)
        if(x.faceLandmarks().first().size < 318)
            return mutableListOf()
        val selectedPoint = mutableListOf(
            x.faceLandmarks()[0][78],
            x.faceLandmarks()[0][80],
            x.faceLandmarks()[0][82],
            x.faceLandmarks()[0][13],
            x.faceLandmarks()[0][312],
            x.faceLandmarks()[0][310],
            x.faceLandmarks()[0][308],
            x.faceLandmarks()[0][88],
            x.faceLandmarks()[0][87],
            x.faceLandmarks()[0][14],
            x.faceLandmarks()[0][317],
            x.faceLandmarks()[0][318]
        )
        return selectedPoint
    }
}