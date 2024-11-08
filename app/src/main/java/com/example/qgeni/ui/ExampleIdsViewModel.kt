package com.example.qgeni.ui

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qgeni.api.ids.IdsGeminiAPI
import com.example.qgeni.api.ids.IdsHostAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExampleIdsViewModel : ViewModel() {
    private var _uiState = MutableStateFlow(ExampleIdsUiState())

    val uiState = _uiState.asStateFlow()

    fun updateHost(host: String) {
        _uiState.update {
            it.copy(host = host)
        }
    }

    fun updatePort(port: String) {
        _uiState.update {
            it.copy(port = port)
        }
    }

    fun updateImageUri(imageUri: Uri) {
        _uiState.update {
            it.copy(imageUri = imageUri)
        }
    }

    fun describeImage(context: Context) {
        val uri = _uiState.value.imageUri
        if (uri == Uri.EMPTY) {
            return
        }

        val stream = context.contentResolver.openInputStream(uri)
        val image = BitmapFactory.decodeStream(stream)

        viewModelScope.launch {
            val geminiResponse = IdsGeminiAPI.describe(image)
            _uiState.update {
                it.copy(
                    geminiResponse = geminiResponse ?: "Something went wrong with Gemini!"
                )
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            IdsHostAPI.setHostPort(_uiState.value.host, _uiState.value.port.toInt())
            val localResponse = IdsHostAPI.describe(image)


            _uiState.update {
                it.copy(
                    serverResponse = localResponse ?: "Something went wrong! Please check Logcat!",
                )
            }
        }


    }

}

data class ExampleIdsUiState(
    val host : String = IdsHostAPI.DEFAULT_HOST,
    val port : String = IdsHostAPI.DEFAULT_PORT.toString(),
    val serverResponse: String = "Local server is ready!",
    val geminiResponse: String = "Gemini is ready!",
    val imageUri : Uri = Uri.EMPTY
)