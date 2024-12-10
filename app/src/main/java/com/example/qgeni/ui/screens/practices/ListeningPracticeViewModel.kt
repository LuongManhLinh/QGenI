package com.example.qgeni.ui.screens.practices

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.qgeni.data.model.ListeningPracticeItem
import com.example.qgeni.data.model.McqQuestion
import com.example.qgeni.data.repository.DefaultListeningRepository
import com.example.qgeni.utils.AudioPlayer
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

    private lateinit var currentAudioPlayer: AudioPlayer
    private lateinit var practiceItem: ListeningPracticeItem

    init {
        viewModelScope.launch(Dispatchers.IO) {
            practiceItem = DefaultListeningRepository.getItem(ObjectId(idHexString))

            _uiState.update {
                it.copy(
                    showLoadingDialog = false,
                    imageList = practiceItem.questionList[0].imageList,
                    questionList = practiceItem.questionList.map { question ->
                        McqQuestion(
                            question = "Choose the correct picture",
                            answerList = List(question.imageList.size) { index ->
                                ('A' + index).toString()
                            }
                        )
                    }
                )
            }

            updateAudioPlayer(0)

            while (true) {
                delay(1000)
                updateTime()
            }
        }
    }



    private fun updateAudioPlayer(index: Int) {
        if (::currentAudioPlayer.isInitialized) {
            currentAudioPlayer.release()
        }

        currentAudioPlayer = AudioPlayer(
            mp3File = practiceItem.questionList[index].mp3File,
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
        updateAudioPlayer(index)
        _uiState.update {
            it.copy(
                currentQuestionIndex = index,
                imageList = practiceItem.questionList[index].imageList,
                playbackState = PlaybackState.PAUSED,
                audioSliderPos = 0f,
                audioDuration = currentAudioPlayer.getDurationSecond()
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
        currentAudioPlayer.release()
        practiceItem.questionList.forEach {
            it.mp3File.delete()
        }
    }
}


data class ListeningPracticeUIState(
    val currentQuestionIndex: Int = 0,
    val imageList: List<Bitmap> = emptyList(),
    val questionList: List<McqQuestion> = emptyList(),

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
