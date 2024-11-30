package com.example.qgeni.ui.screens.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qgeni.data.preferences.UserPreferenceManager
import com.example.qgeni.data.repository.DefaultAccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SignInUIState())
    val uiState = _uiState.asStateFlow()

    fun updateEmail(email: String) {
        _uiState.update {
            it.copy(
                email = email
            )
        }
    }

    fun updatePassword(password: String) {
        _uiState.update {
            it.copy(
                password = password
            )
        }
    }

    fun togglePasswordVisible() {
        _uiState.update {
            it.copy(
                passwordVisible = !it.passwordVisible
            )
        }
    }

    fun signIn(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = DefaultAccountRepository.checkExistence(
                usernameOrEmailOrPhone = uiState.value.email.trim(),
                password = uiState.value.password
            )

            if (userId != null) {
                UserPreferenceManager.saveUserId(context, userId)
                _uiState.update {
                    it.copy(
                        signInEvent = SignInEvent.SUCCESS
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        signInEvent = SignInEvent.FAILURE
                    )
                }
            }
        }
    }
}

data class SignInUIState(
    val email: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val signInEvent: SignInEvent = SignInEvent.IDLE
)

sealed interface SignInEvent {
    data object IDLE : SignInEvent
    data object SUCCESS : SignInEvent
    data object FAILURE : SignInEvent
}