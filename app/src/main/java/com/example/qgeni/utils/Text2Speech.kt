package com.example.qgeni.utils

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import java.util.Locale

private var isSpeaking: Boolean = false
private var isInitializing: Boolean = false
private var textToSpeech: TextToSpeech? = null

fun text2speech(context: Context, text: String) {
    if (isSpeaking || isInitializing) {
        return
    }

    isInitializing = true
    textToSpeech = TextToSpeech(context) { status ->
        isInitializing = false
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech?.let { txtToSpeech ->
                txtToSpeech.setLanguage(Locale.US)
                txtToSpeech.setSpeechRate(0.75f)
                txtToSpeech.setPitch(0.75f)

                txtToSpeech.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String?) {
                        isSpeaking = true
                    }

                    override fun onDone(utteranceId: String?) {
                        isSpeaking = false
                    }

                    override fun onError(utteranceId: String?) {
                        isSpeaking = false
                    }
                })

                // Phát câu nói
                txtToSpeech.speak(
                    text,
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    null
                )
            }
        } else {
            Log.e("TTS", "Initialization failed")
        }
    }
}
