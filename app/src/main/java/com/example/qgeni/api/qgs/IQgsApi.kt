package com.example.qgeni.api.qgs

import QgsForm
import androidx.compose.ui.text.Paragraph

interface IQgsApi {
    suspend fun questionSet(paragraph: String) : List<QgsForm>
}