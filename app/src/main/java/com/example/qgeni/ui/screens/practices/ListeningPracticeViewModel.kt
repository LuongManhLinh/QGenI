package com.example.qgeni.ui.screens.practices

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.qgeni.data.model.ListeningPracticeItem
import com.example.qgeni.data.repository.DefaultListeningRepository
import com.example.qgeni.ui.screens.utils.text2speech
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.bson.types.ObjectId

class ListeningPracticeViewModel(idHexString: String): ViewModel() {
    private val _uiState = MutableStateFlow(ListeningPracticeUIState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val listeningPracticeItem = DefaultListeningRepository.getItem(ObjectId(idHexString))
            _uiState.update {
                it.copy(
                    listeningPracticeItem = listeningPracticeItem
                )
            }

            while (true) {
                delay(1000)
                updateTime()
            }
        }
    }

    fun updateCurrentQuestionIndex(index: Int) {
        _uiState.update {
            it.copy(
                currentQuestionIndex = index
            )
        }
    }

    fun updateAnsweredQuestions(questionIndex: Int, answer: Int) {
        _uiState.update {
            val currentAnswer = it.answeredQuestions.toMutableMap()

            if (currentAnswer[questionIndex] == answer) {
                currentAnswer[questionIndex] = -1
            } else {
                currentAnswer[questionIndex] = answer
            }

            it.copy(
                answeredQuestions = currentAnswer
            )
        }
    }


    private fun updateTime() {
        Log.d("ListeningPracticeViewModel", "updateTime")
        _uiState.update {
            it.copy(
                time = it.time + 1000L
            )
        }
    }

    fun toggleSubmitConfirmDialog(show: Boolean) {
        _uiState.update { it.copy(showSubmitConfirmDialog = show) }
    }

    fun toggleScoreDialog(show: Boolean) {
        _uiState.update { it.copy(showScoreDialog = show) }
    }

    fun play(context: Context) {
        text2speech(
            context = context,
            text = _uiState.value.listeningPracticeItem!!
                .questionList[_uiState.value.currentQuestionIndex].description
        )
    }

    companion object {
        fun factory(idHexString: String): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                ListeningPracticeViewModel(idHexString)
            }
        }
    }
}

data class ListeningPracticeUIState(
    val currentQuestionIndex: Int = 0,
    val listeningPracticeItem: ListeningPracticeItem? = null,

    val showSubmitConfirmDialog: Boolean = false,
    val showScoreDialog: Boolean = false,

    // Key: questionIndex, Value: answerIndex in answerList
    val answeredQuestions: Map<Int, Int?> = emptyMap(),

    val time: Long = 0L
)