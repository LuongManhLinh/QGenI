package com.example.qgeni.ui.screens.practices

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextLayoutInput
import androidx.compose.ui.text.TextLayoutResult
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ReadingPracticeViewModel : ViewModel() {
    private val _readingPracticeUIState = MutableStateFlow(ReadingPracticeUIState())
    val readingPracticeUIState = _readingPracticeUIState.asStateFlow()

    fun toggleHighlightEnabled() {
        _readingPracticeUIState.update { it.copy(isHighlightEnabled = !it.isHighlightEnabled) }
    }

    fun toggleHighlightMode() {
        _readingPracticeUIState.update { it.copy(isHighlightMode = !it.isHighlightMode) }
    }

    fun updateTime() {
        _readingPracticeUIState.update { it.copy(time = it.time + 1000L) }
    }

    fun updateHighlightedIndices(index: Int, isHighlightMode: Boolean) {
        _readingPracticeUIState.update {
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
        _readingPracticeUIState.update { it.copy(textLayoutResult = result) }
    }

    fun updateCurrentQuestionIndex(index: Int) {
        _readingPracticeUIState.update {
            it.copy(
                currentQuestionIndex = index
            )
        }
    }

    fun updateSelectAnswer(selectAnswer: String?) {
        _readingPracticeUIState.update {
            it.copy(
                selectAnswer = selectAnswer
            )
        }
    }

    fun updateAnsweredQuestions(questionIndex: Int, answer: String?) {
        _readingPracticeUIState.update {
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


}


data class ReadingPracticeUIState(

    var isHighlightEnabled: Boolean = false,
    var isHighlightMode: Boolean = true,
    var time: Long = 0L,
    val highlightedIndices: List<Int> = listOf(),
    val textLayoutResult: TextLayoutResult? = null,
    val currentQuestionIndex: Int = 0,
    val selectAnswer: String? = null,
    val answeredQuestions: MutableMap<Int, String> = mutableMapOf(),
)
