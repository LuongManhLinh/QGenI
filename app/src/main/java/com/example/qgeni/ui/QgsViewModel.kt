package com.example.qgeni.ui

import QgsForm
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qgeni.api.qgs.IQgsApi
import com.example.qgeni.api.qgs.QgsGeminiAPI
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QgsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(QgsUIState())
    val uiState = _uiState.asStateFlow()

    fun updateParagraph(paragraph: String) {
        _uiState.update {
            it.copy(
                paragraph = paragraph
            )
        }
        Log.i("Paragraph", paragraph)
    }
    fun fetchQuestions(paragraph: String) {
        viewModelScope.launch {
            try {
                val result = QgsGeminiAPI.questionSet(paragraph)
                _uiState.update {
                    it.copy(
                        listQuestion = result
                    )
                }
                Log.i("List Question", "hello")
            } catch (e: Exception) {
                // Handle exceptions and errors
                e.printStackTrace()
                Log.e("Fetch error", e.toString())
            }
        }
    }

}

data class QgsUIState(
    val paragraph: String = "",
    val listQuestion: List<QgsForm> = emptyList()
)