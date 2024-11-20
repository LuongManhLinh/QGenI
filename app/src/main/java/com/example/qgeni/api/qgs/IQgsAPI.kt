package com.example.qgeni.api.qgs

interface IQgsAPI {
    suspend fun generateQuestions(paragraph: String, numQuestions: Int) : List<QgsForm>
}