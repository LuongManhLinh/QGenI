package com.example.qgeni.ui.screens.practices

import android.app.Dialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.qgeni.data.model.ListeningPracticeItem
import com.example.qgeni.data.repository.DefaultListeningRepository
import com.example.qgeni.utils.AudioGenerator
import com.example.qgeni.utils.AudioPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.bson.types.ObjectId
import java.io.File

class ListeningPracticeViewModel(idHexString: String): ViewModel() {
    private val _uiState = MutableStateFlow(ListeningPracticeUIState())
    val uiState = _uiState.asStateFlow()

    private val mp3FileList = mutableListOf<File>()

    private lateinit var currentAudioPlayer: AudioPlayer

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val listeningPracticeItem = DefaultListeningRepository.getItem(ObjectId(idHexString))

            val results = listeningPracticeItem.questionList.mapIndexed { index, question ->
                async {
                    AudioGenerator.generate(
                        question.description,
                        "$idHexString desc_$index.mp3"
                    )
                }
            }.awaitAll()

            mp3FileList.addAll(results)

            _uiState.update {
                it.copy(
                    showLoadingDialog = false,
                    listeningPracticeItem = listeningPracticeItem
                )
            }

            updateAudioPlayer()

            while (true) {
                delay(1000)
                updateTime()
            }
        }
    }



    private fun updateAudioPlayer() {
        if (mp3FileList.isEmpty()) {
            return
        }
        currentAudioPlayer.release()
        currentAudioPlayer = AudioPlayer(
            mp3File = mp3FileList[_uiState.value.currentQuestionIndex],
            onCompletion = {
                _uiState.update {
                    it.copy(
                        playbackState = PlaybackState.FINISHED
                    )
                }
            }
        )
        _uiState.update {
            it.copy(
                audioDuration = currentAudioPlayer.getDurationSecond()
            )
        }
    }

    fun updateCurrentQuestionIndex(index: Int) {
        _uiState.update {
            it.copy(
                currentQuestionIndex = index
            )
        }
        updateAudioPlayer()
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

    fun play() {
        val playbackState = _uiState.value.playbackState
        if (playbackState == PlaybackState.PAUSED || playbackState == PlaybackState.FINISHED) {
            _uiState.update {
                it.copy(
                    playbackState = PlaybackState.PLAYING
                )
            }
            viewModelScope.launch {
                currentAudioPlayer.play()
                while (_uiState.value.playbackState == PlaybackState.PLAYING) {
                    _uiState.update {
                        it.copy(
                            audioSliderPos = currentAudioPlayer.getPositionSecond()
                        )
                    }
                    delay(100)
                }
            }

        } else if (playbackState == PlaybackState.PLAYING) {
            _uiState.update {
                it.copy(
                    playbackState = PlaybackState.PAUSED
                )
            }
            viewModelScope.launch {
                currentAudioPlayer.pause()
            }
        }
    }

    fun seekTo() {
        viewModelScope.launch {
            currentAudioPlayer.moveTo(
                (_uiState.value.audioSliderPos * 1000).toInt()
            )
        }
    }

    fun updateAudioSliderPos(pos: Float) {
        _uiState.update {
            it.copy(
                audioSliderPos = pos
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

    override fun onCleared() {
        super.onCleared()
        mp3FileList.forEach {
            it.delete()
        }
    }
}


data class ListeningPracticeUIState(
    val currentQuestionIndex: Int = 0,
    val listeningPracticeItem: ListeningPracticeItem? = null,

    val showLoadingDialog: Boolean = true,
    val showSubmitConfirmDialog: Boolean = false,
    val showScoreDialog: Boolean = false,

    // Key: questionIndex, Value: answerIndex in answerList
    val answeredQuestions: Map<Int, Int?> = emptyMap(),

    val time: Long = 0L,

    val audioSliderPos: Float = 0f,
    val audioDuration: Float = 0f,
    val playbackState: PlaybackState = PlaybackState.PAUSED,

)

enum class PlaybackState {
    PLAYING,
    PAUSED,
    FINISHED
}
