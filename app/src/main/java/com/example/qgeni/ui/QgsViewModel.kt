package com.example.qgeni.ui

import com.example.qgeni.api.qgs.QgsForm
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qgeni.api.qgs.QgsGeminiAPI
import kotlinx.coroutines.flow.MutableStateFlow
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
                val result = QgsGeminiAPI.generateQuestions(paragraph, 0)
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