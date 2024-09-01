package com.example.signlanguagedetector.ui.screen

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.navigation.NavController
import com.example.signlanguagedetector.R
import com.example.signlanguagedetector.utilities.SpeechToTexts
import java.util.Locale

object SpeechToText : Screen {
    override val pageTitle: String = "Speech to Text"

    @Composable
    override fun show(navController: NavController) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val scrollState = rememberScrollState()
            val text = remember {
                mutableStateOf("")
            }
            val isListening = remember {
                mutableStateOf(false)
            }
            val context = LocalContext.current
            val activity = context.getActivity()

            val stt = SpeechToTexts(context, text, { isListening.value = false })

            LaunchedEffect(key1 = true) {
                stt.resetSpeechRecognizer()
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(0.6f)
            ) {
                Text(
                    text = text.value,
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .fillMaxSize()
                        .padding(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(60.dp))
            IconButton(onClick = {
                if(isListening.value) {
                    stt.stopListening()
                } else {
                    stt.startListening()
                }
                isListening.value = !isListening.value
            }, modifier = Modifier.size(80.dp)) {
                Icon(
                    painter = painterResource(R.drawable.mic), contentDescription = "",
                    tint = if(isListening.value) Color.Red else Color.Black
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

fun Context.getActivity(): Activity? {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is Activity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    return null
}