package com.example.qgeni.ui.screens.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel : ViewModel() {
    private val _signInUIState = MutableStateFlow(SignInUIState())
    val signInUIState = _signInUIState.asStateFlow()

    fun updateEmail(email: String) {
        _signInUIState.update {
            it.copy(
                email = email
            )
        }
    }

    fun updatePassword(password: String) {
        _signInUIState.update {
            it.copy(
                password = password
            )
        }
    }

    fun togglePasswordVisible() {
        _signInUIState.update {
            it.copy(
                passwordVisible = !it.passwordVisible
            )
        }
    }
}

data class SignInUIState(
    val email: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false
)