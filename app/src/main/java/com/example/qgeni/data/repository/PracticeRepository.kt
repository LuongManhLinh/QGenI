package com.example.qgeni.data.repository

import com.example.qgeni.data.model.PracticeItem
import org.bson.types.ObjectId

interface PracticeRepository {
    suspend fun getAllPracticeItem(
        userId: ObjectId,
        serverAddress: Pair<String, Int>? = null
    ) : List<PracticeItem>
}