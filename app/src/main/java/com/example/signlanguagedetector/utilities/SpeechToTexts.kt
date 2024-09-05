package com.example.signlanguagedetector.utilities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.compose.runtime.MutableState
import java.util.Locale

class SpeechToTexts(
    private val mContext: Context,
    private val text: MutableState<String>,
    private val onEnd: () -> Unit = {}
) {
    private var speechRecognizer: SpeechRecognizer? = null
    private var recognizerIntent: Intent? = null

    private val selectedLanguage = Locale("id-ID").toString()

    init {
        setRecogniserIntent()
    }

    fun startListening() {
        if(speechRecognizer != null)
            speechRecognizer!!.startListening(recognizerIntent)
    }

    fun stopListening() {
        if(speechRecognizer != null)
            speechRecognizer!!.stopListening()
    }

    fun finish() {
        if(speechRecognizer != null) {
            speechRecognizer!!.destroy()
            speechRecognizer = null
        }
    }

    fun resetSpeechRecognizer() {
        if (speechRecognizer != null) speechRecognizer!!.destroy()
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(mContext)
        if (SpeechRecognizer.isRecognitionAvailable(mContext))
            speechRecognizer!!.setRecognitionListener(mRecognitionListener)
        else finish()
    }

    private val mRecognitionListener = object : RecognitionListener {
        override fun onBeginningOfSpeech() {
            println("Recognizer: onBeginningOfSpeech")
        }

        override fun onBufferReceived(buffer: ByteArray) {
            println("Recognizer: onBufferReceived")
        }

        override fun onEndOfSpeech() {
            println("Recognizer: onEndOfSpeech")
            speechRecognizer!!.stopListening()
            onEnd()
        }

        override fun onResults(results: Bundle) {
            println("Recognizer: onResults")
            val matches: ArrayList<String>? = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            var texts = ""
            for (result in matches!!) texts += """
     $result
     
     """.trimIndent()
//            if (IS_CONTINUES_LISTEN) {
//                startListening()
//            } else {
//                binding.progressBar1.visibility = View.GONE
//            }
            text.value = texts
        }

        override fun onError(errorCode: Int) {
            println("Recognizer: onError")

            // rest voice recogniser
            resetSpeechRecognizer()
            startListening()
        }

        override fun onEvent(arg0: Int, arg1: Bundle) {
            println("Recognizer: onEvent")
        }

        override fun onPartialResults(arg0: Bundle) {
            println("Recognizer: onPartialResults")
        }

        override fun onReadyForSpeech(arg0: Bundle) {
            println("Recognizer: onReadyForSpeech")
        }

        override fun onRmsChanged(rmsdB: Float) {
            println("Recognizer: onRmsChanged")
        }
    }

    private fun setRecogniserIntent() {
        recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        recognizerIntent!!.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
            selectedLanguage
        )
        recognizerIntent!!.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            selectedLanguage
        )
        recognizerIntent!!.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        recognizerIntent!!.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 20)
    }


}