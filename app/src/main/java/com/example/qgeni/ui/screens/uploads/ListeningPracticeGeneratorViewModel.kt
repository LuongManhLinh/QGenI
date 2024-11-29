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
                image = image
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

        viewModelScope.launch {
            val practiceItem = IdsApplication.getListeningPracticeItem(
                _uiState.value.image!!,
                5
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
            return
        }

        viewModelScope.launch(Dispatchers.IO) {

            DefaultListeningRepository.insert(
                listeningPracticeItem!!.copy(
                    title = _uiState.value.practiceTitle
                )
            )
        }
    }
}



data class ListeningGeneratorUIState(
    val showUploadFileDialog: Boolean = false,
    val selectedOption: String = "",
    val currentState: GeneratorState = GeneratorState.Idle,
    val numQuestion: String = "1",
    val image: Bitmap? = null,
    val practiceTitle: String = ""
)


