package com.example.qgeni.ui.screens.practices

import com.example.qgeni.data.model.PracticeItem
import com.example.qgeni.data.preferences.UserPreferenceManager
import com.example.qgeni.data.repository.DefaultListeningRepository
import org.bson.types.ObjectId

open class ListeningPracticeListViewModel : PracticeListViewModel() {
    override suspend fun getPracticeItemList(): List<PracticeItem> {
        val userId = UserPreferenceManager.getUserId() ?: return emptyList()
        return DefaultListeningRepository.getAllPracticeItem(userId)
    }

    override suspend fun deleteItem(id: ObjectId) {
        DefaultListeningRepository.deleteItem(id)
    }

    override suspend fun changeItemToOld(id: ObjectId) {
        DefaultListeningRepository.changeToOld(id)
    }
}