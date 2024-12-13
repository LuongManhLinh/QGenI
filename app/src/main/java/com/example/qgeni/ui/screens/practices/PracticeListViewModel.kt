package com.example.qgeni.ui.screens.practices

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qgeni.data.model.PracticeItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.bson.types.ObjectId


abstract class PracticeListViewModel : ViewModel() {
    private val _practiceListUIState = MutableStateFlow(PracticeListUIState())
    open val practiceListUIState = _practiceListUIState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _practiceListUIState.update {
                it.copy(
                    practiceItemList = getPracticeItemList(),
                    loading = false
                )
            }
        }
    }

    protected abstract suspend fun getPracticeItemList(): List<PracticeItem>
    protected abstract suspend fun deleteItem(id: ObjectId)
    protected abstract suspend fun changeItemToOld(id: ObjectId)

    fun deleteItem() {
        val id = _practiceListUIState.value.selectedItemId

        viewModelScope.launch(Dispatchers.IO) {
            deleteItem(id!!)
        }

        val newItemList = _practiceListUIState.value.practiceItemList.filterNot {
            it.id == id
        }

        _practiceListUIState.update {
            it.copy(
                practiceItemList = newItemList,
                showDeleteDialog = false,
                selectedIdx = null
            )
        }
    }

    fun toggleDeleteDialog(show: Boolean) {
        _practiceListUIState.update { it.copy(showDeleteDialog = show) }
    }

    fun toggleOpenDialog(show: Boolean) {
        _practiceListUIState.update { it.copy(showOpenDialog = show) }
    }

    fun selectItem(index: Int) {
        _practiceListUIState.update { it.copy(selectedIdx = index) }
    }

    fun changeItemToOld() {
        val selectedIdx = _practiceListUIState.value.selectedIdx ?: return

        if (!_practiceListUIState.value.practiceItemList[selectedIdx].isNew) {
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            changeItemToOld(_practiceListUIState.value.selectedItemId!!)
        }
    }

}


data class PracticeListUIState(
    val practiceItemList: List<PracticeItem> = emptyList(),
    val showDeleteDialog: Boolean = false,
    val showOpenDialog: Boolean = false,
    val selectedIdx: Int? = null,
    val loading: Boolean = true
) {
    val selectedItemId: ObjectId?
        get() = selectedIdx?.let { practiceItemList[it].id }
}
