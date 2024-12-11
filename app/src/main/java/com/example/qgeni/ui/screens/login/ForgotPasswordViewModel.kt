package com.example.qgeni.ui.screens.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ForgotPasswordViewModel: ViewModel() {
    private val _forgotPasswordUIState = MutableStateFlow(ForgotPasswordUIState())
    val forgotPasswordUIState = _forgotPasswordUIState.asStateFlow()

    fun updateEmail(email: String) {
        _forgotPasswordUIState.update {
            it.copy(
                email = email 
            )
        }
    }

    fun reset() {
        _forgotPasswordUIState.update {
            it.copy(
                isEmailError = false
            )
        }
    }

    fun checkEmpty(): Boolean {
        return !_forgotPasswordUIState.value.isEmailError
    }

    fun updateConstraint() {
        _forgotPasswordUIState.update {
            it.copy(
                isEmailError = it.email == ""
            )
        }
    }
}

data class ForgotPasswordUIState(
    val email: String = "",
    val isEmailError: Boolean = true,
)