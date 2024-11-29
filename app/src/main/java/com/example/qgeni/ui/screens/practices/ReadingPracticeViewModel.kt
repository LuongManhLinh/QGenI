package com.example.qgeni.ui.screens.practices

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextLayoutInput
import androidx.compose.ui.text.TextLayoutResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.qgeni.data.model.ReadingPracticeItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ReadingPracticeViewModel(idHexString: String) : ViewModel() {
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
    val selectAnswer: String? = null,
    val answeredQuestions: MutableMap<Int, String> = mutableMapOf(),
)
