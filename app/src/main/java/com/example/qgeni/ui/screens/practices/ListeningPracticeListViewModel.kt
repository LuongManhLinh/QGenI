package com.example.qgeni.ui.screens.practices

import com.example.qgeni.data.model.PracticeItem
import com.example.qgeni.data.preferences.UserPreferenceManager
import com.example.qgeni.data.repository.DefaultListeningRepository
import com.example.qgeni.data.repository.ListeningRepository

class ListeningPracticeListViewModel : PracticeListViewModel() {
    override suspend fun getPracticeItemList(): List<PracticeItem> {
        return DefaultListeningRepository
            .getAllPracticeItem(
                UserPreferenceManager.getUserId()
            )
    }
}