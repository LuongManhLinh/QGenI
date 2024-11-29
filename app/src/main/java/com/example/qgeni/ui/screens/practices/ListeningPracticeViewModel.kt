package com.example.qgeni.ui.screens.practices

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.qgeni.data.model.ListeningPracticeItem
import com.example.qgeni.data.repository.DefaultListeningRepository
import com.example.qgeni.ui.screens.utils.text2speech
import kotlinx.coroutines.Dispatchers
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
        }

        viewModelScope.launch {
            if (
                _uiState.value.listeningPracticeItem != null
            ) {
                while (true) {
                    updateTime()
                    kotlinx.coroutines.delay(1000)
                }
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

    private fun updateTime() {
        _uiState.update {
            it.copy(
                time = it.time + 1000L
            )
        }
    }


    fun updateSelectAnswer(selectAnswer: String?) {
        _uiState.update {
            it.copy(
                selectAnswer = selectAnswer
            )
        }
    }

    fun submit() {

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
    val answeredQuestions: MutableMap<Int, String> = mutableMapOf(),
    val time: Long = 0L,
    val selectAnswer: String? = null
)