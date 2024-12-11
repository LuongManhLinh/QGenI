package com.example.qgeni.ui.screens.uploads

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qgeni.data.api.ids.IdsHostAPI
import com.example.qgeni.data.repository.DefaultListeningRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.bson.types.ObjectId

class ListeningPracticeGeneratorViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ListeningGeneratorUIState())
    val uiState = _uiState.asStateFlow()

    private var itemId: ObjectId? = null

    fun updateUploadFileDialogVisibility(showUploadFileDialog: Boolean) {
        _uiState.update {
            it.copy(
                showUploadFileDialog = showUploadFileDialog
            )
        }
    }

    fun updateSelectedImage(imageList: List<Bitmap>) {
        _uiState.update {
            it.copy(
                imageList = imageList,
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


    fun createListeningPractice() {
        if (_uiState.value.imageList.isEmpty()) {
            return
        }

        _uiState.update {
            it.copy(
                currentState = GeneratorState.Loading
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            val id = IdsHostAPI.createListeningPracticeItem(
                _uiState.value.imageList
            )

            if (id != null) {
                _uiState.update {
                    it.copy(
                        currentState = GeneratorState.Titling
                    )
                }
                itemId = id
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


    fun saveListeningPracticeTitle() {
        if (itemId == null) {
            _uiState.update {
                it.copy(
                    currentState = GeneratorState.Error
                )
            }
        }

        _uiState.update {
            it.copy(
                currentState = GeneratorState.Saving
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            DefaultListeningRepository.changeTitle(
                itemId!!,
                _uiState.value.practiceTitle
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
                imageList = emptyList(),
                practiceTitle = ""
            )
        }
    }
}



data class ListeningGeneratorUIState(
    val showUploadFileDialog: Boolean = false,
    val currentState: GeneratorState = GeneratorState.Idle,
    val imageList: List<Bitmap> = emptyList(),
    val practiceTitle: String = ""
)


