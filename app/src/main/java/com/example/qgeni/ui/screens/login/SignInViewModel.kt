package com.example.qgeni.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qgeni.data.repository.DefaultAccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.bson.types.ObjectId

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

    fun signIn() : ObjectId? {
        var userId: ObjectId? = null
        viewModelScope.launch(Dispatchers.IO) {
            userId = DefaultAccountRepository.checkExistence(
                usernameOrEmailOrPhone = signInUIState.value.email.trim(),
                password = signInUIState.value.password
            )
        }
        return userId
    }
}

data class SignInUIState(
    val email: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false
)