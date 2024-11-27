package com.example.qgeni.data.repository


interface ReadingRepository {
    suspend fun insertReading()
}


class DefaultReadingRepository {
    object Names {
        const val COLLECTION_NAME = "reading"
        const val USER_ID = "userId"
        const val PARAGRAPH = "paragraph"
        const val STATEMENTS = "statements"
        const val ANSWERS = "answers"
    }
}