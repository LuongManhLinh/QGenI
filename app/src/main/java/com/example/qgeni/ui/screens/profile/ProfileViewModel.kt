package com.example.qgeni.ui.screens.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qgeni.data.repository.DefaultAccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.bson.Document
import org.bson.types.ObjectId

class ProfileViewModel: ViewModel() {
    private val _userInfo = MutableStateFlow<Document?>(null)
    val userInfo = _userInfo.asStateFlow()

    fun loadUserInfo(userId: ObjectId) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userInfo = DefaultAccountRepository.getUserInfo(userId)
                Log.i("Debug loading userinfo", userInfo.toString())
                _userInfo.value = userInfo
            } catch (e: Exception) {
                Log.e("Error loading userinfo", "Error: ${e.message}", e)
            }
        }
    }

}
