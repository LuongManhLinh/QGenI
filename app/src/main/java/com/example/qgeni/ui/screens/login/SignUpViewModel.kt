package com.example.qgeni.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qgeni.data.repository.DefaultAccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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


    fun signUp() {

        viewModelScope.launch(Dispatchers.IO) {
            DefaultAccountRepository.createAccount(
                username = _signUpUIState.value.username,
                password = _signUpUIState.value.password,
                email = _signUpUIState.value.email.trim().ifEmpty {
                    null
                },
                phoneNumber = _signUpUIState.value.phoneNumber.trim().ifEmpty {
                    null
                }
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