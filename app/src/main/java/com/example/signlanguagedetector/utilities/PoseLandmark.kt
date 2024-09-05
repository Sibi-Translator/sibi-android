package com.example.signlanguagedetector.utilities

import android.content.Context
import android.graphics.Bitmap
import androidx.camera.core.ImageProxy
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.framework.image.MPImage
import com.google.mediapipe.tasks.components.containers.NormalizedLandmark
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarker

class PoseLandmark(
    private val context: Context
) {
    val baseOptionsBuilder = BaseOptions.builder().setModelAssetPath("pose_landmarker_lite.task")
    val baseOptions = baseOptionsBuilder.build()

    val options = PoseLandmarker.PoseLandmarkerOptions.builder()
        .setBaseOptions(baseOptions)
        .setNumPoses(1)
        .setMinTrackingConfidence(0.5f)
        .setMinPosePresenceConfidence(0.5f)
        .setMinPosePresenceConfidence(0.5f)
        .build()
    val poseLandmarker = PoseLandmarker.createFromOptions(context, options)

    fun detect(image: MPImage): MutableList<MutableList<NormalizedLandmark>> {
        val x = poseLandmarker.detect(image)
        return x.landmarks()
    }
}