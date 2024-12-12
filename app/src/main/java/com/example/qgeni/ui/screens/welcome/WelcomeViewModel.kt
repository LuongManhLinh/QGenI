package com.example.qgeni.ui.screens.welcome

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.qgeni.data.DefaultConnection
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

    fun updateCtrlPort(port: String) {
        _uiState.update {
            it.copy(
                ctrlPort = port
            )
        }
    }

    fun showDialog(show: Boolean) {
        _uiState.update {
            it.copy(
                showDialog = show
            )
        }
    }

    fun getPort(context: Context) {
        val dbPort = PortPreferenceManager.loadDbPort(context)
        val genPort = PortPreferenceManager.loadGenPort(context)
        val ctrlPort = PortPreferenceManager.loadCtrlPort(context)
        _uiState.update {
            it.copy(
                dbPort = dbPort.toString(),
                genPort = genPort.toString(),
                ctrlPort = ctrlPort.toString(),
                firstInit = false
            )
        }
    }

    fun writePort(context: Context) {
        val dbPort = _uiState.value.dbPort.toInt()
        val genPort = _uiState.value.genPort.toInt()
        val ctrlPort = _uiState.value.ctrlPort.toInt()
        PortPreferenceManager.saveDbPort(context, dbPort)
        PortPreferenceManager.saveGenPort(context, genPort)
        PortPreferenceManager.saveCtrlPort(context, ctrlPort)

        DefaultConnection.dbPort = dbPort
        DefaultConnection.genPort = genPort
        DefaultConnection.ctrlPort =  ctrlPort
    }

}

data class PortState(
    var showDialog: Boolean = false,
    val dbPort: String = "",
    val genPort: String = "",
    val ctrlPort : String = "",
    var firstInit: Boolean = true
)