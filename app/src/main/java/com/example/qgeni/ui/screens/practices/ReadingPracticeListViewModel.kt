package com.example.qgeni.ui.screens.practices

import com.example.qgeni.data.model.PracticeItem
import com.example.qgeni.data.preferences.UserPreferenceManager
import com.example.qgeni.data.repository.DefaultReadingRepository
import org.bson.types.ObjectId

class ReadingPracticeListViewModel : PracticeListViewModel() {
    override suspend fun getPracticeItemList(): List<PracticeItem> {
        return DefaultReadingRepository
            .getAllPracticeItem(
                UserPreferenceManager.getUserId()!!
            )
    }

    override suspend fun deleteItem(id: ObjectId) {
        DefaultReadingRepository.deleteItem(id)
    }

    override suspend fun changeItemToOld(id: ObjectId) {
        DefaultReadingRepository.changeToOld(id)
    }
}