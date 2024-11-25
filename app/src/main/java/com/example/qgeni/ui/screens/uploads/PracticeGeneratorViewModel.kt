package com.example.qgeni.ui.screens.uploads

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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

    fun updateReadingInputParagraph(text: String) {
        _readingUIState.update {
            it.copy(
                inputParagraph = text
            )
        }
    }
}


data class ListeningPracticeGeneratorUIState(
    val showUploadFileDialog: Boolean = false,
    val selectedOption: String = "Chọn model",
    val currentState: GeneratorState = GeneratorState.Idle
)

data class ReadingPracticeGeneratorUIState(
    val showUploadFileDialog: Boolean = false,
    val selectedOption: String = "Chọn model",
    val currentState: GeneratorState = GeneratorState.Idle,
    val isUploadMode: Boolean = true,
    val inputParagraph: String = ""
)