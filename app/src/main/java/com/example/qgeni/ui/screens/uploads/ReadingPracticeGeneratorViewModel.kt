
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qgeni.data.api.qgs.QgsGeminiAPI
import com.example.qgeni.application.QgsApplication
import com.example.qgeni.data.model.McqQuestion
import com.example.qgeni.data.model.ReadingPracticeItem
import com.example.qgeni.data.repository.DefaultListeningRepository
import com.example.qgeni.data.repository.DefaultReadingRepository
import com.example.qgeni.ui.screens.uploads.GeneratorState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader

open class ReadingPracticeGeneratorViewModel : ViewModel() {

    private val _readingUIState = MutableStateFlow(ReadingPracticeGeneratorUIState())
    val readingUIState = _readingUIState.asStateFlow()

    private var readingPracticeItem: ReadingPracticeItem? = null

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

    fun saveReadingPractice() {
        if (readingPracticeItem == null) {
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
            DefaultReadingRepository.insert(
                readingPracticeItem!!.copy(
                    title = _readingUIState.value.title
                )
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
        if(isFullInfo() == "") {
            viewModelScope.launch {
                var input: String
                if(_readingUIState.value.isUploadMode)
                    input = _readingUIState.value.fileContent
                else
                    input = _readingUIState.value.inputParagraph
                val practiceItem = QgsApplication.getReadingPracticeItem(
                    input,
                    _readingUIState.value.inputNumStatement.toInt()
                )
                Log.i("Item practice", practiceItem.toString())
                if (practiceItem != null) {
                    _readingUIState.update {
                        it.copy(
                            currentState = GeneratorState.Titling,
                        )
                    }

                    readingPracticeItem = practiceItem

                } else {
                    _readingUIState.update {
                        it.copy(
                            currentState = GeneratorState.Error
                        )
                    }
                }
            }
        }
    }

    fun updateGenerateSuccess(isSuccess: Boolean) {
        _readingUIState.update {
            it.copy(
                isGenerateSuccess = isSuccess
            )
        }
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
    val isUploadMode: Boolean = true,
    val title: String = "",
    val inputParagraph: String = "",
    val inputNumStatement: String = "1",
    val listReadingQuestion: List<McqQuestion> = emptyList(),
    val isGenerateSuccess: Boolean = false,
    val textUri: Uri = Uri.EMPTY,
    val fileName: String = "",
    val fileContent: String = "",
    val isFullInfo: Boolean = false
)