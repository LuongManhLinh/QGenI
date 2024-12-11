package com.example.qgeni.data.repository

import com.example.qgeni.data.model.PracticeItem
import org.bson.types.ObjectId

interface PracticeRepository {
    suspend fun getAllPracticeItem(
        userId: ObjectId
    ) : List<PracticeItem>

    suspend fun changeTitle(
        id: ObjectId,
        title: String
    )

    suspend fun deleteItem(
        id: ObjectId
    )

    suspend fun changeToOld(
        id: ObjectId
    )

    suspend fun changeHighestScore(
        id: ObjectId,
        highestScore: String
    )

}