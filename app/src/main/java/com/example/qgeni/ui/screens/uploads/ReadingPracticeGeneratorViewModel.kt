package com.example.qgeni.ui.screens.uploads

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qgeni.application.QgsApplication
import com.example.qgeni.data.model.McqQuestion
import com.example.qgeni.data.repository.DefaultReadingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.bson.types.ObjectId
import java.io.BufferedReader
import java.io.InputStreamReader

open class ReadingPracticeGeneratorViewModel : ViewModel() {

    private val _readingUIState = MutableStateFlow(ReadingPracticeGeneratorUIState())
    open val readingUIState = _readingUIState.asStateFlow()

    private var itemId: ObjectId? = null

    fun updateReadingUploadFileDialog(show: Boolean) {
        _readingUIState.update { it.copy(showUploadFileDialog = show) }
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

    fun updateTitle(title: String){
        _readingUIState.update {
            it.copy(
                title = title
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

    fun updateReadingInputNumStatement(numStatement: String) {
        _readingUIState.update {
            it.copy(
                inputNumStatement = numStatement
            )
        }
    }


   open fun saveReadingPractice() {
        if (itemId == null) {

            _readingUIState.update {
                it.copy(
                    currentState = GeneratorState.Error
                )
            }
        }

        _readingUIState.update {
            it.copy(
                currentState = GeneratorState.Saving
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            DefaultReadingRepository.changeTitle(
                itemId!!,
                _readingUIState.value.title
            )

            _readingUIState.update {
                it.copy(
                    currentState = GeneratorState.Success
                )
            }
        }
    }

    fun createReadingPractice() {
        _readingUIState.update {
            it.copy(
                currentState = GeneratorState.Loading
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            val id = QgsApplication.getReadingPracticeItem(
                _readingUIState.value.inputParagraph,
                _readingUIState.value.inputNumStatement.toInt()
            )

            if (id != null) {

                _readingUIState.update {
                    it.copy(
                        currentState = GeneratorState.Titling,
                    )
                }

                itemId = id

            } else {
                _readingUIState.update {
                    it.copy(
                        currentState = GeneratorState.Error
                    )
                }
            }
        }
    }


    fun updateTextUri(context: Context, uri: Uri) {
        val fileName: String
        val fileContent: String
        if(uri != Uri.EMPTY) {
            fileName = getFileName(context, uri)
            fileContent = readFileContent(context, uri)
        }
        else {
            fileName = ""
            fileContent = ""
        }
        _readingUIState.update {
            it.copy(
                textUri = uri,
                fileName = fileName,
                fileContent = fileContent
            )
        }
        updateReadingInputParagraph(fileContent)
    }

    fun isFullInfo(): String {
        if(_readingUIState.value.isUploadMode) {
            if(_readingUIState.value.fileContent == "")
                return "Vui lòng upload file"
        }
        else {
            if(_readingUIState.value.inputParagraph == "")
                return "Vui lòng dán đoạn văn"
        }
        if(_readingUIState.value.inputNumStatement == "")
            return "Vui lòng nhập số câu hỏi"
        return ""
    }

    fun increaseNumStatement() {
        val numStatement = _readingUIState.value.inputNumStatement.toInt() + 1
        _readingUIState.update {
            it.copy(
                inputNumStatement = numStatement.toString()
            )
        }
    }
    
    fun decreaseNumStatement() {
        var numStatement = _readingUIState.value.inputNumStatement.toInt() - 1
        if (numStatement < 0) {
            numStatement = 0
        }

        if(numStatement > 1) {
            _readingUIState.update {
                it.copy(
                    inputNumStatement = numStatement.toString()
                )
            }
        }
    }
}

private fun getFileName(context: Context, uri: Uri): String {
    var fileName = ""
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val columnIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (columnIndex != -1) {
                fileName = it.getString(columnIndex)
            }
        }
    }
    return fileName
}

private fun readFileContent(context: Context, uri: Uri): String {
    val inputStream = context.contentResolver.openInputStream(uri)
    val reader = BufferedReader(InputStreamReader(inputStream))
    return reader.readText()
}


data class ReadingPracticeGeneratorUIState(
    val showUploadFileDialog: Boolean = false,
    val selectedOption: String = "Chọn model",
    val currentState: GeneratorState = GeneratorState.Idle,
    val isUploadMode: Boolean = false,
    val title: String = "",
    val inputParagraph: String = "",
    val inputNumStatement: String = "1",
    val listReadingQuestion: List<McqQuestion> = emptyList(),
    val isGenerateSuccess: Boolean = false,
    val textUri: Uri = Uri.EMPTY,
    val fileName: String = "",
    val fileContent: String = ""
)