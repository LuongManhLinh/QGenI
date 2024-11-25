package com.example.qgeni.ui.screens.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class VerificationViewModel: ViewModel() {
    private val _verificationUIState = MutableStateFlow(VerificationUIState())
    val verificationUIState = _verificationUIState.asStateFlow()

    fun updateOtp(otp: String) {
        _verificationUIState.update {
            it.copy(
                otp = otp
            )
        }
    }
}

data class VerificationUIState(
    val otp: String = ""
)