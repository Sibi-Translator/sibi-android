package com.example.signlanguagedetector.ui.component

import android.content.Context
import android.view.Surface
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.signlanguagedetector.analyzer.SLAnalyzer
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Composable
fun cameraX(
    prediction: MutableState<String>,
    isActive: MutableState<Boolean>,
    lensFacing: Int = CameraSelector.LENS_FACING_FRONT,
    slAnalyzer: SLAnalyzer
) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current

    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val cameraProvider = cameraProviderFuture.get()
    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }
    var preview by remember { mutableStateOf<Preview?>(null) }
    val executor = ContextCompat.getMainExecutor(context)
    var imageAnalysis: ImageAnalysis? by remember {
        mutableStateOf(null)
    }

    val previewView = remember {
        PreviewView(context)
    }

    var imageTaken by remember { mutableStateOf("") }
    var cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing).build()

    LaunchedEffect(key1 = isActive.value) {
        println("Cupcake is changed to ${isActive.value}")
        if(isActive.value) {
            slAnalyzer.isPaused = false
        } else {
            slAnalyzer.isPaused = true
            slAnalyzer.predict()
        }
    }

    AndroidView(
        factory = { context ->
            imageCapture = ImageCapture.Builder()
                .setTargetRotation(Surface.ROTATION_0)
                .build()

            cameraProviderFuture.addListener({
                imageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                imageAnalysis!!.setAnalyzer(
                    ContextCompat.getMainExecutor(context),
                    slAnalyzer
                )

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycle,
                    cameraSelector,
                    preview,
                    imageAnalysis
                )
            }, executor)
            preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }
            previewView
        },
        modifier = Modifier
            .fillMaxWidth(),
        update = {
            cameraProviderFuture.addListener({
                imageCapture = ImageCapture.Builder()
                    .setTargetRotation(Surface.ROTATION_0)
                    .build()

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycle,
                    cameraSelector,
                    preview,
                    imageAnalysis
                )
            }, executor)
        }
    )
}

private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { cameraProvider ->
        cameraProvider.addListener({
            continuation.resume(cameraProvider.get())
        }, ContextCompat.getMainExecutor(this))
    }
}