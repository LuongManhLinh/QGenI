package com.example.qgeni.data.repository

import kotlinx.coroutines.runBlocking
import org.bson.Document
import org.bson.types.ObjectId
import java.util.Date


data class ReadingItem(
    val userId: ObjectId,
    val title: String,
    val passage: String,
    val creationDate: Date,
    val statements: List<String>,
    val answers: List<String>,
    val isNew: Boolean
)


interface ReadingRepository {
    suspend fun insert(
        item: ReadingItem,
        serverAddress: Pair<String, Int>? = null
    )
    suspend fun getAll(
        userId: ObjectId,
        serverAddress: Pair<String, Int>? = null
    ): List<ReadingItem>
}


object DefaultReadingRepository : ReadingRepository {
    object Names {
        const val COLLECTION_NAME = "reading"
        const val USER_ID = "userId"
        const val TITLE = "title"
        const val CREATION_DATE = "creationDate"
        const val PASSAGE = "passage"
        const val STATEMENTS = "statements"
        const val ANSWERS = "answers"
        const val IS_NEW = "isNew"
    }

    override suspend fun insert(
        item: ReadingItem,
        serverAddress: Pair<String, Int>?
    ) {
        val document = Document(
            Names.USER_ID, item.userId
        ).append(
            Names.TITLE, item.title
        ).append(
            Names.CREATION_DATE, item.creationDate
        ).append(
            Names.PASSAGE, item.passage
        ).append(
            Names.STATEMENTS, item.statements
        ).append(
            Names.ANSWERS, item.answers
        ).append(
            Names.IS_NEW, item.isNew
        )

        val collection = DefaultMongoDBService.getCollection(Names.COLLECTION_NAME, serverAddress)

        collection.insertOne(document)
    }

    override suspend fun getAll(
        userId: ObjectId,
        serverAddress: Pair<String, Int>?
    ): List<ReadingItem> {
        val collection = DefaultMongoDBService.getCollection(Names.COLLECTION_NAME, serverAddress)

        val cursor = collection.find(Document(Names.USER_ID, userId))

        return cursor.toList().map { document ->
            ReadingItem(
                userId = document?.get(Names.USER_ID) as ObjectId,
                title = document[Names.TITLE] as String,
                creationDate = document[Names.CREATION_DATE] as Date,
                passage = document[Names.PASSAGE] as String,
                statements = document[Names.STATEMENTS] as List<String>,
                answers = document[Names.ANSWERS] as List<String>,
                isNew = document[Names.IS_NEW] as Boolean
            )
        }
    }
}

