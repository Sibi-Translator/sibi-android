package com.example.signlanguagedetector.ui.screen

import androidx.camera.core.CameraSelector
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.signlanguagedetector.R
import com.example.signlanguagedetector.ui.component.cameraX
import com.example.signlanguagedetector.ui.theme.SignLanguageDetectorTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object SignToText : Screen {
    override val pageTitle: String = "Sign to Text"

    @Composable
    override fun show(navController: NavController) {
        val prediction = remember {
            mutableStateOf("")
        }
        val isActive = remember {
            mutableStateOf(false)
        }

        SignLanguageDetectorTheme {
            Surface(color = Color(0xffF7EDEC), modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val lensFacing = remember {
                        mutableStateOf(CameraSelector.LENS_FACING_FRONT)
                    }
                    val started = remember {
                        mutableStateOf(false)
                    }
                    val context = LocalContext.current
                    val coroutine = rememberCoroutineScope()
                    LaunchedEffect(key1 = true) {
                        coroutine.launch {
                            delay(500)
                            started.value = true
                        }
                    }
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f)
                        .background(Color(0xffF7EDEC)),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if(started.value) {
                            Column(
                                Modifier
                                    .fillMaxWidth(0.9f)
                                    .clip(RoundedCornerShape(20.dp))) {
                                cameraX(prediction, isActive, lensFacing.value)
                            }
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        val isPlaying = remember { mutableStateOf(false) }
                        IconButton(onClick = {
                            if(isPlaying.value) {
                                isPlaying.value = false
                                // TODO: Add predict logic
                            }
                        },
                            modifier = Modifier.size(50.dp)
                        ) {
                            if(isPlaying.value) {
                                Icon(painter = painterResource(id = R.drawable.stop), "")
                            }
                        }
                        IconButton(onClick = {
                            isPlaying.value = !isPlaying.value
                        },
                            modifier = Modifier.size(80.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = if(isPlaying.value) R.drawable.pause_circle else R.drawable.play),
                                ""
                            )
                        }
                        IconButton(onClick = {
                            if(lensFacing.value == CameraSelector.LENS_FACING_FRONT)
                                lensFacing.value = CameraSelector.LENS_FACING_BACK
                            else
                                lensFacing.value = CameraSelector.LENS_FACING_FRONT
                        },
                            modifier = Modifier.size(50.dp)
                        ) {
                            Icon(painter = painterResource(id = R.drawable.camera_switch), contentDescription = "",
                                modifier = Modifier.size(50.dp))
                        }
                    }
                }
            }
        }
    }
}