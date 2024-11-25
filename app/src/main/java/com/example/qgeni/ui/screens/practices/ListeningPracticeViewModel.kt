package com.example.qgeni.ui.screens.practices

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ListeningPracticeViewModel: ViewModel() {
    private val _listeningPracticeUIState = MutableStateFlow(ListeningPracticeUIState())
    val listeningPracticeUIState = _listeningPracticeUIState.asStateFlow()

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
}

data class ListeningPracticeUIState(
    val currentQuestionIndex: Int = 0,
    val answeredQuestions: MutableMap<Int, String> = mutableMapOf(),
    val time: Long = 0L,
    val playbackState: PlaybackState = PlaybackState.Paused,
    val sliderPosition: Float = 0f,
    val selectAnswer: String? = null
)