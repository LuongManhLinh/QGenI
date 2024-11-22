package com.example.qgeni.api.qgs

import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel

object QgsGeminiAPI : IQgsAPI {
    private const val REQUEST = "Create 5 True False Not Given questions with the format Question 1: and the next line Answer: "
    private const val LOG_TAG = "GEMINI API"
    private var modelName = "gemini-1.5-flash"
    private var key = "AIzaSyB4pLbUwPN1DuEQdmaziZ4SzKcUgb11nkg"

    private val generativeModel = GenerativeModel(
        modelName = modelName,
        apiKey = key
    )

    override suspend fun generateQuestions(paragraph: String, numQuestions: Int): List<QgsForm> {
        val output = mutableListOf<QgsForm>()
        val input = paragraph + REQUEST
        Log.i("INPUT", input)
        try {
            val response = generativeModel.generateContent(input)
            Log.i(LOG_TAG, response.text.toString())
            val listQuestion = response.text.toString().trimIndent()
            val pattern = Regex("""\*\*Question \d+:\*\* ([^\n]+)\n\*\*Answer:\*\* (True|False|Not Given)""")
            val matches = pattern.findAll(listQuestion)
            Log.i("QS", matches.toString())
            for (match in matches) {
                val statement = match.groups[1]?.value ?: ""
                val answer = match.groups[2]?.value ?: ""
                output.add(QgsForm(statement, answer))
            }
        } catch (e: Exception) {
            Log.e("Gemini false", "Exception occur: $e")
        }

        return output
    }

}