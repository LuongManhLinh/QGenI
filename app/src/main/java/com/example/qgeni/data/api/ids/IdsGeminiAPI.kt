package com.example.qgeni.data.api.ids

import android.graphics.Bitmap
import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content

object IdsGeminiAPI : IDescribeOnlyAPI {
    private const val PROMPT = "Describe the given picture with IELTS vocabulary within 20 words"
    private const val LOG_TAG = "GEMINI API"
    private var modelName = "gemini-1.5-flash"
    private var apiKey = "AIzaSyDFFKBL6voUpM7FQOprnOqTFReG0Kcdfcs"

    private val model = GenerativeModel(
        modelName = modelName,
        apiKey = apiKey
    )

    override suspend fun describe(image: Bitmap): String {
        try {
            val response = model.generateContent(
                content {
                    image(image)
                    text(PROMPT)
                }
            )

            return response.text?.substringAfter(":")?.trim() ?: ""

        } catch (e: Exception) {
            Log.e(LOG_TAG, "Exception occurred: $e")
            return ""
        }
    }

    suspend fun describeMany(images: List<Bitmap>): List<String> {
        return images.map { describe(it) }
    }
}