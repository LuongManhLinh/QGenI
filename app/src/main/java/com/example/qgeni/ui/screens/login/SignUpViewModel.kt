package com.example.qgeni.ui.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qgeni.data.repository.DefaultAccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUIState())
    val uiState = _uiState.asStateFlow()

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
                phoneNumber = phoneNumber
            )
        }
    }

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

    fun toggleTermsAccepted() {
        _uiState.update {
            it.copy(
                termsAccepted = !it.termsAccepted
            )
        }
    }

    fun showSuccessDialog(isShow: Boolean) {
        _uiState.update {
            it.copy(
                showSuccessDialog = isShow
            )
        }
    }

    fun signUp() {

        viewModelScope.launch(Dispatchers.IO) {
            DefaultAccountRepository.createAccount(
                username = _uiState.value.username,
                password = _uiState.value.password,
                email = _uiState.value.email.trim().ifEmpty {
                    null
                },
                phoneNumber = _uiState.value.phoneNumber.trim().ifEmpty {
                    null
                }
            )
            _uiState.update {
                it.copy(
                    showSuccessDialog = true
                )
            }
        }
    }

    fun reset() {
        _uiState.update {
            it.copy(
                isAccountError = false,
                isPasswordError = false,
                isEmailError = false,
                isPhoneNumberError = false
            )
        }
    }

    fun checkEmpty(): Boolean {
        return !_uiState.value.isAccountError && !_uiState.value.isPasswordError && !_uiState.value.isEmailError && !_uiState.value.isPhoneNumberError
    }

    fun updateConstraint() {
        _uiState.update {
            it.copy(
                isAccountError = it.username == "",
                isPasswordError = it.password == "",
                isEmailError = it.email == "",
                isPhoneNumberError = it.phoneNumber == ""
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
    val showSuccessDialog: Boolean = false,
    val isAccountError: Boolean = true,
    val isPasswordError: Boolean = true,
    val isEmailError: Boolean = true,
    val isPhoneNumberError: Boolean = true
)