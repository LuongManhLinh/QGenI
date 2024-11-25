package com.example.qgeni.ui.screens.practices

import android.view.View
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


open class BasePracticeListViewModel : ViewModel() {
    private val _practiceListUIState = MutableStateFlow(PracticeListUIState())
    val practiceListUIState = _practiceListUIState.asStateFlow()

    fun toggleDeleteDialog(show: Boolean) {
        _practiceListUIState.update { it.copy(showDeleteDialog = show) }
    }

    fun toggleOpenDialog(show: Boolean) {
        _practiceListUIState.update { it.copy(showOpenDialog = show) }
    }

    fun selectItem(id: Int) {
        _practiceListUIState.update { it.copy(selectedItemId = id) }
    }
}

class ListeningPracticeListViewModel : BasePracticeListViewModel() {
}

class ReadingPracticeListViewModel : BasePracticeListViewModel() {
}


data class PracticeListUIState(
    val showDeleteDialog: Boolean = false,
    val showOpenDialog: Boolean = false,
    val selectedItemId: Int = -1
)
