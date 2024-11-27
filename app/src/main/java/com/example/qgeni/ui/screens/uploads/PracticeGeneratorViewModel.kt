package com.example.qgeni.ui.screens.uploads

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qgeni.api.qgs.QgsForm
import com.example.qgeni.api.qgs.QgsGeminiAPI
import com.example.qgeni.data.model.McqQuestion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class BasePracticeGeneratorViewModel : ViewModel() {
    private val _listeningUIState = MutableStateFlow(ListeningPracticeGeneratorUIState())
    val listeningUIState = _listeningUIState.asStateFlow()

    private val _readingUIState = MutableStateFlow(ReadingPracticeGeneratorUIState())
    val readingUIState = _readingUIState.asStateFlow()

    fun updateListeningUploadFileDialog(show: Boolean) {
        _listeningUIState.update { it.copy(showUploadFileDialog = show) }
    }

    fun selectListeningOption(option: String) {
        _listeningUIState.update { it.copy(selectedOption = option) }
    }

    fun updateListeningGeneratorState(state: GeneratorState) {
        _listeningUIState.update { it.copy(currentState = state) }
    }

    fun updateReadingUploadFileDialog(show: Boolean) {
        _readingUIState.update { it.copy(showUploadFileDialog = show) }
    }

    fun selectReadingOption(option: String) {
        _readingUIState.update { it.copy(selectedOption = option) }
    }

    fun updateReadingGeneratorState(state: GeneratorState) {
        _readingUIState.update { it.copy(currentState = state) }
    }

    fun updateReadingUploadMode() {
        _readingUIState.update {
            it.copy(
                isUploadMode = !it.isUploadMode
            )
        }
    }

    fun updateTitle(title: String){
        _readingUIState.update {
            it.copy(
                title = title
            )
        }
    }
    fun updateReadingInputParagraph(text: String) {
        _readingUIState.update {
            it.copy(
                inputParagraph = text
            )
        }
    }

    fun updateReadingInputNumStatement(numStatement: String) {
        _readingUIState.update {
            it.copy(
                inputNumStatement = numStatement
            )
        }
    }

    suspend fun fetchReadingQuestions(paragraph: String): List<McqQuestion> {
        return try {
            val result = QgsGeminiAPI.generateQuestions(
                paragraph,
                _readingUIState.value.inputNumStatement.toInt()
            )
            val resultMcqQuestion: List<McqQuestion> = result.map { qgsForm ->
                McqQuestion(
                    question = qgsForm.statement,
                    answerList = listOf("True", "False", "Not given"),
                    correctAnswer = qgsForm.answer
                )
            }
            resultMcqQuestion.forEach { mcqQuestion ->
                Log.i("readingQuestion", mcqQuestion.question)
                Log.i("answerList", mcqQuestion.answerList.joinToString { ", " })
                Log.i("answer", mcqQuestion.correctAnswer)
            }
            _readingUIState.update {
                it.copy(
                    listReadingQuestion = resultMcqQuestion,
                )
            }
            Log.d("Fetch success", result.toString())
            resultMcqQuestion
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("Fetch error", e.toString())
            emptyList() // Return empty list if there is an error
        }
    }

    fun updateGenerateSuccess(isSuccess: Boolean) {
        _readingUIState.update {
            it.copy(
                isGenerateSuccess = isSuccess
            )
        }
    }
}


data class ListeningPracticeGeneratorUIState(
    val showUploadFileDialog: Boolean = false,
    val selectedOption: String = "Chọn model",
    val currentState: GeneratorState = GeneratorState.Idle,
)

data class ReadingPracticeGeneratorUIState(
    val showUploadFileDialog: Boolean = false,
    val selectedOption: String = "Chọn model",
    val currentState: GeneratorState = GeneratorState.Idle,
    val isUploadMode: Boolean = false,
    val title: String = "",
    val inputParagraph: String = "",
    val inputNumStatement: String = "",
    val listReadingQuestion: List<McqQuestion> = emptyList(),
    val isGenerateSuccess: Boolean = false
)