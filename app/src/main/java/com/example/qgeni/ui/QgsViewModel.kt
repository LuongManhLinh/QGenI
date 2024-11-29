package com.example.qgeni.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qgeni.api.qgs.QgsForm
import com.example.qgeni.api.qgs.QgsGeminiAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

//class QgsViewModel : ViewModel() {
//    private val _uiState = MutableStateFlow(QgsUIState())
//    val uiState = _uiState.asStateFlow()
//
//    fun updateParagraph(paragraph: String) {
//        _uiState.update {
//            it.copy(
//                paragraph = paragraph
//            )
//        }
//    }
//
//    fun updateNumStatement(numStatement: String) {
//        _uiState.update {
//            it.copy(
//                numStatement = numStatement
//            )
//        }
//    }
//
//    fun fetchQuestions(paragraph: String) {
//        viewModelScope.launch {
//            try {
//                val result = QgsGeminiAPI.generateQuestions(
//                    paragraph,
//                    _uiState.value.numStatement.toInt()
//                )
//                _uiState.update {
//                    it.copy(
//                        listQuestion = result
//                    )
//                }
//                Log.d("Fetch success", result.toString())
//            } catch (e: Exception) {
//                // Handle exceptions and errors
//                e.printStackTrace()
//                Log.e("Fetch error", e.toString())
//            }
//        }
//    }

//}

//data class QgsUIState(
//    val paragraph: String = "Computers are important devices in our daily lives. They help us work, learn, and connect with others. With fast processors and useful programs, computers can perform many tasks quickly and easily. They are used in schools, businesses, and homes for tasks like writing, creating, and researching. As technology improves, computers continue to become even more helpful.",
//    val numStatement: String = "10",
//    val listQuestion: List<QgsForm> = emptyList()
//)