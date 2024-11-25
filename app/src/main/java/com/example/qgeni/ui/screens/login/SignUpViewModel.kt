package com.example.qgeni.ui.screens.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignUpViewModel : ViewModel() {
    private val _signUpUIState = MutableStateFlow(SignUpUIState())
    val signUpUIState = _signUpUIState.asStateFlow()

    fun updateUsername(username: String) {
        _signUpUIState.update {
            it.copy(
                username = username
            )
        }
    }

    fun updatePhoneNumber(phoneNumber: String) {
        _signUpUIState.update {
            it.copy(
                phoneNumber = phoneNumber
            )
        }
    }

    fun updateEmail(email: String) {
        _signUpUIState.update {
            it.copy(
                email = email
            )
        }
    }
    fun updatePassword(password: String) {
        _signUpUIState.update {
            it.copy(
                password = password
            )
        }
    }

    fun togglePasswordVisible() {
        _signUpUIState.update {
            it.copy(
                passwordVisible = !it.passwordVisible
            )
        }
    }
    fun toggleTermsAccepted() {
        _signUpUIState.update {
            it.copy(
                termsAccepted = !it.termsAccepted
            )
        }
    }
}

data class SignUpUIState(
    val username: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val termsAccepted: Boolean = false,
)