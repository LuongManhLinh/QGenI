package com.example.qgeni.ui.screens.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qgeni.data.repository.DefaultAccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.bson.types.ObjectId

class ChangeInfoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ChangeInfoUIState())
    val uiState = _uiState.asStateFlow()

    fun loadUserInfo(userId: ObjectId) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userInfo = DefaultAccountRepository.getUserInfo(userId)
                _uiState.update {
                    it.copy(
                        username = userInfo?.getString(DefaultAccountRepository.Names.USERNAME) ?: _uiState.value.username,
                        phoneNumber = userInfo?.getString(DefaultAccountRepository.Names.PHONE_NUMBER) ?: _uiState.value.phoneNumber,
                        email = userInfo?.getString(DefaultAccountRepository.Names.EMAIL) ?: _uiState.value.email,
                    )
                }
                
            } catch (e: Exception) {
                Log.e("Error loading userinfo", "Error: ${e.message}", e)
            }
        }
    }

    fun updateUserInfo(userId: ObjectId, username: String?, phoneNumber: String?, email: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val isUpdated = DefaultAccountRepository.updateUserInfo(userId, username, phoneNumber, email)
            if (isUpdated) {
                // Nếu cập nhật thành công, tải lại thông tin người dùng
                loadUserInfo(userId)
            } else {
                // Xử lý nếu cập nhật không thành công
            }
        }
    }
    fun updateUsername(username: String) {
        _uiState.update {
            it.copy(
                username = username
            )
        }
    }

    fun updatePhoneNumber(phoneNumber: String) {
        _uiState.update {
            it.copy(
                username = phoneNumber
            )
        }
    }

    fun updateEmail(email: String) {
        _uiState.update {
            it.copy(
                username = email
            )
        }
    }

    fun updateGender(gender: String) {
        _uiState.update {
            it.copy(
                username = gender
            )
        }
    }

}

data class ChangeInfoUIState(
    val username: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val gender: String = ""
)