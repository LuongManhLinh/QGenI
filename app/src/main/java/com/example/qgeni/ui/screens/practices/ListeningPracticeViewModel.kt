package com.example.qgeni.ui.screens.practices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.qgeni.data.model.ListeningPracticeItem
import com.example.qgeni.data.repository.DefaultListeningRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.bson.types.ObjectId

class ListeningPracticeViewModel(idHexString: String): ViewModel() {
    private val _listeningPracticeUIState = MutableStateFlow(ListeningPracticeUIState())
    val listeningPracticeUIState = _listeningPracticeUIState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val listeningPracticeItem = DefaultListeningRepository.getItem(ObjectId(idHexString))
            _listeningPracticeUIState.update {
                it.copy(
                    listeningPracticeItem = listeningPracticeItem
                )
            }
        }
    }

    fun updateCurrentQuestionIndex(index: Int) {
        _listeningPracticeUIState.update {
            it.copy(
                currentQuestionIndex = index
            )
        }
    }

    fun updateAnsweredQuestions(questionIndex: Int, answer: String?) {
        _listeningPracticeUIState.update {
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

    fun updateTime() {
        _listeningPracticeUIState.update {
            it.copy(
                time = it.time + 1000L
            )
        }
    }

    fun updatePlaybackState(playbackState: PlaybackState) {
        _listeningPracticeUIState.update {
            it.copy(
                playbackState = playbackState
            )
        }
    }

    fun updateSliderPosition(newPosition: Float) {
        _listeningPracticeUIState.update {
            it.copy(
                sliderPosition = newPosition
            )
        }
    }

    fun updateSelectAnswer(selectAnswer: String?) {
        _listeningPracticeUIState.update {
            it.copy(
                selectAnswer = selectAnswer
            )
        }
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
    val playbackState: PlaybackState = PlaybackState.Paused,
    val sliderPosition: Float = 0f,
    val selectAnswer: String? = null
)