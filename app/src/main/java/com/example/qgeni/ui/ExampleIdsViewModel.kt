package com.example.qgeni.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qgeni.api.ids.IdsAPI
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
            it.copy(
                imageUri = imageUri,
                responseImages = emptyList()
            )
        }
    }

    fun updateNumDesiredImage(numDesiredImage: String) {
        _uiState.update {
            it.copy(numDesiredImage = numDesiredImage)
        }
    }

    fun getSimilarImage(context: Context) {
        val uri = _uiState.value.imageUri
        if (uri == Uri.EMPTY) {
            return
        }

        val stream = context.contentResolver.openInputStream(uri)
        val image = BitmapFactory.decodeStream(stream)

        IdsAPI.setHostPort(
            _uiState.value.host,
            uiState.value.port.toInt()
        )

        viewModelScope.launch(Dispatchers.IO) {
            val response = IdsAPI.getSimilarImage(image, _uiState.value.numDesiredImage.toInt())
            _uiState.update {
                it.copy(responseImages = response)
            }
        }

    }

}

data class ExampleIdsUiState(
    val host : String = IdsAPI.DEFAULT_HOST,
    val port : String = IdsAPI.DEFAULT_PORT.toString(),
    val numDesiredImage: String = "10",
    val imageUri : Uri = Uri.EMPTY,
    val responseImages: List<Bitmap> = emptyList()
)