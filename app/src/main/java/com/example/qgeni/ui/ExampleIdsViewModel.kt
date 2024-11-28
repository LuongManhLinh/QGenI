package com.example.qgeni.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qgeni.api.CommunicationUtils
import com.example.qgeni.api.CommunicationUtils.DEFAULT_HOST
import com.example.qgeni.api.CommunicationUtils.DEFAULT_PORT
import com.example.qgeni.api.ids.IdsHostAPI
import com.example.qgeni.application.IdsApplication
import com.example.qgeni.data.repository.DefaultListeningRepository
import com.example.qgeni.data.repository.ListeningItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.bson.types.ObjectId
import java.util.Date

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
                responseImgAndDesc = emptyList()
            )
        }
    }

    fun updateNumDesiredImage(numDesiredImage: String) {
        _uiState.update {
            it.copy(numQuestion = numDesiredImage)
        }
    }

    fun getFiles(context: Context, fileUri: Uri) {
        val text = context.contentResolver.openInputStream(fileUri)?.bufferedReader().use {
            it?.readText()
        }
        Log.d("ExampleIdsViewModel", "text: $text")
    }

    fun getSimilarImage(context: Context) {
        val uri = _uiState.value.imageUri
        if (uri == Uri.EMPTY) {
            return
        }

        val stream = context.contentResolver.openInputStream(uri)
        val image = BitmapFactory.decodeStream(stream)

        IdsHostAPI.setHostPort(
            _uiState.value.host,
            uiState.value.port.toInt()
        )

        viewModelScope.launch(Dispatchers.IO) {
            val startTime = System.currentTimeMillis()
            IdsHostAPI.setHostPort(
                _uiState.value.host,
                uiState.value.port.toInt()
            )
            val response = IdsHostAPI.getSimilarImage(image, _uiState.value.numQuestion.toInt())


            val endTime = System.currentTimeMillis()
            _uiState.update {
                it.copy(
                    responseImgAndDesc = response,
                    responseTime = endTime - startTime
                )
            }

        }

    }

}

data class ExampleIdsUiState(
    val host : String = DEFAULT_HOST,
    val port : String = DEFAULT_PORT.toString(),
    val numQuestion: String = "4",
    val imageUri : Uri = Uri.EMPTY,
    val responseImgAndDesc: List<Bitmap> = emptyList(),
    val responseTime: Long = 0
)