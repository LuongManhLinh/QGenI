package com.example.qgeni.api.qgs

interface IQgsAPI {
    suspend fun generate(paragraph: String) : List<QgsForm>
}