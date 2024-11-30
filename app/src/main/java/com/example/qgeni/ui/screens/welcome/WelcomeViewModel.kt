package com.example.qgeni.ui.screens.welcome

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.qgeni.data.preferences.PortPreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WelcomeViewModel : ViewModel() {
    private val _uiState  = MutableStateFlow(PortState())
    val uiState = _uiState.asStateFlow()


    fun updateDBPort(port: String) {
        _uiState.update {
            it.copy(
                dbPort = port
            )
        }
    }

    fun updateGenPort(port: String) {
        _uiState.update {
            it.copy(
                genPort = port
            )
        }
    }

    fun showDialog() {
        _uiState.update {
            it.copy(
                showDialog = true
            )
        }
    }

    fun getPort(context: Context) {
        val dbPort = PortPreferenceManager.loadDbPort(context)
        val genPort = PortPreferenceManager.loadGenPort(context)
        _uiState.update {
            it.copy(
                dbPort = dbPort.toString(),
                genPort = genPort.toString(),
                firstInit = false
            )
        }
    }

    fun writePort(context: Context) {
        PortPreferenceManager.saveDbPort(context, _uiState.value.dbPort.toInt())
        PortPreferenceManager.saveGenPort(context, _uiState.value.genPort.toInt())
    }

}

data class PortState(
    var showDialog: Boolean = false,
    val dbPort: String = "",
    val genPort: String = "",
    var firstInit: Boolean = true
)