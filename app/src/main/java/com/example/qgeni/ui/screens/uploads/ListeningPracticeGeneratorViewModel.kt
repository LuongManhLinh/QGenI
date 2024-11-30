package com.example.qgeni.ui.screens.uploads

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qgeni.application.IdsApplication
import com.example.qgeni.data.model.ListeningPracticeItem
import com.example.qgeni.data.repository.DefaultListeningRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListeningPracticeGeneratorViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ListeningGeneratorUIState())
    val uiState = _uiState.asStateFlow()

    private var listeningPracticeItem : ListeningPracticeItem? = null

    fun updateUploadFileDialogVisibility(showUploadFileDialog: Boolean) {
        _uiState.update {
            it.copy(
                showUploadFileDialog = showUploadFileDialog
            )
        }
    }

    fun updateSelectedImage(image: Bitmap) {
        _uiState.update {
            it.copy(
                image = image,
                showUploadFileDialog = false
            )
        }
    }

    fun updateCurrentState(state: GeneratorState) {
        _uiState.update {
            it.copy(
                currentState = state
            )
        }
    }

    fun updateNumQuestion(numQuestion: String) {
        _uiState.update {
            it.copy(
                numQuestion = numQuestion
            )
        }
    }

    fun createListeningPractice() {
        if (_uiState.value.image == null) {
            return
        }

        _uiState.update {
            it.copy(
                currentState = GeneratorState.Loading
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            val practiceItem = IdsApplication.getListeningPracticeItem(
                _uiState.value.image!!,
                _uiState.value.numQuestion.toInt()
            )

            if (practiceItem != null) {
                _uiState.update {
                    it.copy(
                        currentState = GeneratorState.Saving
                    )
                }

                listeningPracticeItem = practiceItem
            } else {
                _uiState.update {
                    it.copy(
                        currentState = GeneratorState.Error
                    )
                }
            }


        }
    }


    fun updatePracticeTitle(title: String) {
        _uiState.update {
            it.copy(
                practiceTitle = title
            )
        }
    }

    fun saveListeningPractice() {
        if (listeningPracticeItem == null) {
            _uiState.update {
                it.copy(
                    currentState = GeneratorState.Error
                )
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            DefaultListeningRepository.insert(
                listeningPracticeItem!!.copy(
                    title = _uiState.value.practiceTitle
                )
            )

            _uiState.update {
                it.copy(
                    currentState = GeneratorState.Success
                )
            }
        }
    }

    fun reset() {
        _uiState.update {
            it.copy(
                showUploadFileDialog = false,
                currentState = GeneratorState.Idle,
                numQuestion = it.numQuestion,
                image = null,
                practiceTitle = ""
            )
        }
    }
}



data class ListeningGeneratorUIState(
    val showUploadFileDialog: Boolean = false,
    val currentState: GeneratorState = GeneratorState.Idle,
    val numQuestion: String = "1",
    val image: Bitmap? = null,
    val practiceTitle: String = ""
)


