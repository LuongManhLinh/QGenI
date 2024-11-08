package com.example.qgeni.ui

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

class QgsViewModel : ViewModel() {
    val generativeModel: GenerativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = "AIzaSyCcwQSq5RrmcsHgb-pHaxjqozGeTvwDtfw"
    )

    private val _responseText = mutableStateOf("")
    val responseText: State<String> = _responseText

    fun sendParagraph(par: String){
        Log.i("ViewModel", par)
        val question = par + "tạo 5 câu hỏi dạng true false not given có trong đoạn văn trên và đáp án ở mỗi cuối câu"
        viewModelScope.launch {
            val chat = generativeModel.startChat()
            val response =chat.sendMessage(question)
            _responseText.value = response.text.toString()
            Log.i("Response from Gemini", response.text.toString())
        }
    }
}