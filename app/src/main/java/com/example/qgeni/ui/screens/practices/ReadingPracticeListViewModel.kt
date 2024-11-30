package com.example.qgeni.ui.screens.practices

import com.example.qgeni.data.model.PracticeItem
import com.example.qgeni.data.preferences.UserPreferenceManager
import com.example.qgeni.data.repository.DefaultReadingRepository

class ReadingPracticeListViewModel : PracticeListViewModel() {
    override suspend fun getPracticeItemList(): List<PracticeItem> {
        return DefaultReadingRepository
            .getAllPracticeItem(
                UserPreferenceManager.getUserId()!!
            )
    }
}