package com.example.signlanguagedetector.ui.screen

import android.app.Activity
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
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.navigation.NavController
import com.example.signlanguagedetector.R
import java.util.Locale

object SpeechToText : Screen {
    override val pageTitle: String = "Speech to Text"

    @Composable
    override fun show(navController: NavController) {
        val scrollState = rememberScrollState()
        val text = remember {
            mutableStateOf("")
        }
        val isListening = remember {
            mutableStateOf(false)
        }
        val context = LocalContext.current
        var speechRecognizer: SpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
        var speechRecognizerIntent: Intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        var isFirstListen = true
        var tts: TextToSpeech? = null

        if(tts == null) {
            tts = TextToSpeech(context) {
                if (it == TextToSpeech.SUCCESS) {
                    tts?.language = Locale("id-ID")
                }
            }
        }

        LaunchedEffect(key1 = true) {
            isFirstListen = true
            if(tts == null) {
                tts = TextToSpeech(context) {
                    if (it == TextToSpeech.SUCCESS) {
                        tts?.language = Locale("id-ID")
                    }
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(0.8f)
                    .background(Color.White, shape = RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.BottomEnd
            ) {
                OutlinedTextField(
                    value = text.value,
                    onValueChange = {
                        text.value = it
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        unfocusedTextColor = Color.Black,
                        focusedTextColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .fillMaxWidth(1f)
                        .padding(20.dp)
                        .border(width = 0.dp, color = Color.Transparent),
                    shape = RoundedCornerShape(16.dp),
                    placeholder = {
                        Text(text = "Tap here to edit text to speech")
                    }
                )
//                Text(
//                    text = text.value,
//                    modifier = Modifier
//                        .verticalScroll(scrollState)
//                        .fillMaxWidth()
//                        .fillMaxHeight()
//                        .padding(20.dp),
//                    textAlign = TextAlign.Start
//                )
                IconButton(onClick = {
                    println("Text is ${text.value}")
                    tts?.speak(text.value, TextToSpeech.QUEUE_FLUSH, null, null)
                }) {
                    Icon(painter = painterResource(id = R.drawable.baseline_volume_up_24), contentDescription = "")
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
            IconButton(onClick = {
                if(isListening.value) {
                    isListening.value = false
                    speechRecognizer.stopListening()
                } else {
                    speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
                    speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                    speechRecognizerIntent.putExtra(
                        RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                    )
                    speechRecognizerIntent.putExtra(
                        RecognizerIntent.EXTRA_LANGUAGE,
                        "id-ID"
                    )
                    speechRecognizerIntent.putExtra(
                        RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                        selectedLanguage
                    )
                    speechRecognizerIntent.putExtra(
                        RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE,
                        selectedLanguage
                    )

                    speechRecognizer.setRecognitionListener(object : RecognitionListener {
                        override fun onReadyForSpeech(bundle: Bundle?) {
                            println("Cucpake Ready")
                        }
                        override fun onBeginningOfSpeech() {
                            println("Cucpake Begin")
                        }
                        override fun onRmsChanged(v: Float) {}
                        override fun onBufferReceived(bytes: ByteArray?) {
                            println("Cucpake Buffer")
                        }
                        override fun onEndOfSpeech() {
                            isListening.value = false
                        }

                        override fun onError(errorCode: Int) {
                            if(isFirstListen) {
                                isFirstListen = false
                                return
                            }
                            val message = when (errorCode) {
                                SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
                                SpeechRecognizer.ERROR_CLIENT -> "Client side error"
                                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Insufficient permissions"
                                SpeechRecognizer.ERROR_NETWORK -> "Please check your network"
                                // Add other cases based on SpeechRecognizer error codes
                                else -> "Unknown error"
                            }
                            println("Error code $errorCode")
                            Toast.makeText(context, "Error occurred: $message", Toast.LENGTH_SHORT).show()
                        }


                        override fun onResults(bundle: Bundle) {
                            val result = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                            if (result != null) {
                                // attaching the output
                                // to our textview
                                text.value = result[0]
                            }
                            println("Cupcake Results: $result")
                        }

                        override fun onPartialResults(bundle: Bundle) {
                            val result = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                            println("Cupcake Partial Results ${result?.joinToString("##")}")
                            if(result != null) {
                                text.value = result.joinToString(" ")
                            }
                        }
                        override fun onEvent(i: Int, bundle: Bundle?) {
                            println("Cupcake Event $i")
                        }

                    })
                    speechRecognizer.startListening(speechRecognizerIntent)
                    isListening.value = true
                }
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

private val selectedLanguage = "id-ID"