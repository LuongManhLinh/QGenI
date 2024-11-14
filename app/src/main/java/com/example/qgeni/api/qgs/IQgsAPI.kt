package com.example.qgeni.api.qgs

interface IQgsAPI {
    suspend fun questionSet(paragraph: String) : List<QgsForm>
}