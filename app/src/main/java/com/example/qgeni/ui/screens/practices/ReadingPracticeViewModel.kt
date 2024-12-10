package com.example.qgeni.ui.screens.practices

import androidx.compose.ui.text.TextLayoutResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.qgeni.data.model.ReadingPracticeItem
import com.example.qgeni.data.repository.DefaultReadingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.bson.types.ObjectId

open class ReadingPracticeViewModel(idHexString: String) : ViewModel() {
    private val _uiState = MutableStateFlow(ReadingPracticeUIState())
    open val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val readingPracticeItem = DefaultReadingRepository.getItem(ObjectId(idHexString))
            _uiState.update {
                it.copy(
                    readingPracticeItem = readingPracticeItem
                )
            }
        }

        viewModelScope.launch {
            if (
                _uiState.value.readingPracticeItem != null
            ) {
                while (true) {
                    updateTime()
                    kotlinx.coroutines.delay(1000)
                }
            }
        }

    }

    fun toggleHighlightEnabled() {
        _uiState.update { it.copy(isHighlightEnabled = !it.isHighlightEnabled) }
    }

    fun toggleHighlightMode() {
        _uiState.update { it.copy(isHighlightMode = !it.isHighlightMode) }
    }

    fun updateTime() {
        _uiState.update {
            if(_uiState.value.isComplete)
                it.copy(time = it.time)
            else
                it.copy(time = it.time + 1000L)
        }
    }

    fun updateHighlightedIndices(index: Int, isHighlightMode: Boolean) {
        _uiState.update {
            val newIndices = it.highlightedIndices.toMutableList()
            if(isHighlightMode) {
                if(!newIndices.contains(index)) {
                    newIndices.add(index)
                }
            } else
                newIndices.remove(index)
            it.copy(
                highlightedIndices = newIndices
            )
        }
    }

    fun updateTextLayoutResult(result: TextLayoutResult?) {
        _uiState.update { it.copy(textLayoutResult = result) }
    }

    fun updateCurrentQuestionIndex(index: Int) {
        _uiState.update {
            it.copy(
                currentQuestionIndex = index
            )
        }
    }

    fun toggleSubmitConfirmDialog(show: Boolean) {
        _uiState.update { it.copy(showSubmitConfirmDialog = show) }
    }

    fun toggleScoreDialog(show: Boolean) {
        _uiState.update { it.copy(showScoreDialog = show) }
    }

    fun updateSelectAnswer(selectAnswer: String?) {
        _uiState.update {
            it.copy(
                selectAnswer = selectAnswer
            )
        }
    }

    fun updateAnsweredQuestions(questionIndex: Int, answer: String?) {
        _uiState.update {
            val currentAnswer = it.answeredQuestions.toMutableMap()
            if (answer != null)
                currentAnswer[questionIndex] = answer
            else
                currentAnswer.remove(questionIndex)
            it.copy(
                answeredQuestions = currentAnswer
            )
        }
    }

    fun updateIsComplete(isComplete: Boolean) {
        _uiState.update {
            it.copy(
                isComplete = isComplete
            )
        }
    }

    open fun checkScore(): Int {
        var score = 0
        for ((index, answer) in _uiState.value.readingPracticeItem?.questionList?.withIndex()!!) {
            val correctAnswer = answer.answer.toString()
            val userAnswer = _uiState.value.answeredQuestions[index]
            if(correctAnswer == userAnswer)
                score++
        }
        return score
    }

    companion object {
        fun factory(idHexString: String): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                ReadingPracticeViewModel(idHexString)
            }
        }
    }


}


data class ReadingPracticeUIState(
    val readingPracticeItem: ReadingPracticeItem? = null,
    var isHighlightEnabled: Boolean = false,
    var isHighlightMode: Boolean = true,
    var time: Long = 0L,
    val highlightedIndices: List<Int> = listOf(),
    val textLayoutResult: TextLayoutResult? = null,
    val currentQuestionIndex: Int = 0,
    val showSubmitConfirmDialog: Boolean = false,
    val showScoreDialog: Boolean = false,
    val selectAnswer: String? = null,
    val answeredQuestions: MutableMap<Int, String> = mutableMapOf(),
    val isComplete: Boolean = false
)
