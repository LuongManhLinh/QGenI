package com.example.qgeni.ui.screens.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChangeInfoViewModel : ViewModel() {
    private val _changeInfoUIState = MutableStateFlow(ChangeInfoUIState())
    val changeInfoUIState = _changeInfoUIState.asStateFlow()

    fun updateUsername(username: String) {
        _changeInfoUIState.update {
            it.copy(
                username = username
            )
        }
    }
    fun updatePhoneNumber(phoneNumber: String) {
        _changeInfoUIState.update {
            it.copy(
                username = phoneNumber
            )
        }
    }fun updateEmail(email: String) {
        _changeInfoUIState.update {
            it.copy(
                username = email
            )
        }
    }
    fun updateGender(gender: String) {
        _changeInfoUIState.update {
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