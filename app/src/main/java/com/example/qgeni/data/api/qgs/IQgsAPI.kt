package com.example.qgeni.data.api.qgs

import com.example.qgeni.data.model.ReadingQuestion

interface IQgsAPI {
    suspend fun generateQuestions(paragraph: String, numQuestions: Int) : List<ReadingQuestion>
}