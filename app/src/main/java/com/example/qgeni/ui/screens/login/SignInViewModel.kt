package com.example.qgeni.ui.screens.login

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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

    fun reset() {
        _uiState.update {
            it.copy(
                isFailure = false,
                isAccountError = false,
                isPasswordError = false,
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
                        signInEvent = SignInEvent.FAILURE,
                        isFailure = true
                    )
                }
            }
        }
    }

    fun checkEmpty(): Boolean {
        return !_uiState.value.isAccountError and !_uiState.value.isPasswordError
    }

    fun updateConstraint() {
        _uiState.update {
            it.copy(
                isAccountError = (it.email == ""),
                isPasswordError = (it.password == "")
            )
        }
    }
}

data class SignInUIState(
    val email: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val signInEvent: SignInEvent = SignInEvent.IDLE,
    val isAccountError: Boolean = false,
    val isPasswordError: Boolean = false,
    val isFailure: Boolean = false
)

sealed interface SignInEvent {
    data object IDLE : SignInEvent
    data object SUCCESS : SignInEvent
    data object FAILURE : SignInEvent
}