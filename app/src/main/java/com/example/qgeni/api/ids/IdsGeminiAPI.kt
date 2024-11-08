package com.example.qgeni.api.ids

import android.graphics.Bitmap
import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content

object IdsGeminiAPI : IIdsAPI {
    private const val PROMPT = "Describe the given picture with IELTS vocabulary within 30 words"
    private const val LOG_TAG = "GEMINI API"
    private var modelName = "gemini-1.5-flash"
    private var apiKey = "AIzaSyDFFKBL6voUpM7FQOprnOqTFReG0Kcdfcs"

    private val model = GenerativeModel(
        modelName = modelName,
        apiKey = apiKey
    )

    override suspend fun describe(image: Bitmap): String? {
        try {
            val response = model.generateContent(
                content {
                    image(image)
                    text(PROMPT)
                }
            )

            return response.text

        } catch (e: Exception) {
            Log.e(LOG_TAG, "Exception occur: $e")
            return null
        }
    }
}