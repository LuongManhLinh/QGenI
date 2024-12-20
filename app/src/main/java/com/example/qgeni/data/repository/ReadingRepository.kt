package com.example.qgeni.data.repository

import com.example.qgeni.data.model.PracticeItem
import com.example.qgeni.data.model.ReadingAnswer
import com.example.qgeni.data.model.ReadingPracticeItem
import com.example.qgeni.data.model.ReadingQuestion
import org.bson.Document
import org.bson.types.ObjectId
import java.util.Date


interface ReadingRepository : PracticeRepository {
    suspend fun getItem(
        id: ObjectId
    ): ReadingPracticeItem
}


object DefaultReadingRepository : ReadingRepository {
    object Names {
        const val COLLECTION_NAME = "reading"
        const val ID = "_id"
        const val USER_ID = "userId"
        const val TITLE = "title"
        const val CREATION_DATE = "creationDate"
        const val PASSAGE = "passage"
        const val QUESTIONS = "questions"
        const val Q_STATEMENT = "statement"
        const val Q_ANSWER = "answer"
        const val IS_NEW = "isNew"
        const val HIGHEST_SCORE = "highestScore"
    }


    override suspend fun getItem(
        id: ObjectId
    ): ReadingPracticeItem {

        val collection = DefaultMongoDBService.getCollection(Names.COLLECTION_NAME)
        val cursor = collection.find(Document(Names.ID, id))
        val document = cursor.first()

        return ReadingPracticeItem(
            id = document?.get(Names.ID) as ObjectId,
            title = document[Names.TITLE] as String,
            creationDate = document[Names.CREATION_DATE] as Date,
            passage = document[Names.PASSAGE] as String,
            isNew = document[Names.IS_NEW] as Boolean,
            highestScore = document[Names.HIGHEST_SCORE] as String?,
            questionList = (document[Names.QUESTIONS] as List<Document>).map {
                ReadingQuestion(
                    statement = it[Names.Q_STATEMENT] as String,
                    answer = ReadingAnswer.fromInt(
                        when (it[Names.Q_ANSWER]) {
                            is Int -> it[Names.Q_ANSWER] as Int
                            is Long -> (it[Names.Q_ANSWER] as Long).toInt()
                            else -> throw IllegalArgumentException("Invalid answer type")
                        }
                    )
                )
            }
        )
    }


    override suspend fun changeTitle(id: ObjectId, title: String) {
        val collection = DefaultMongoDBService.getCollection(Names.COLLECTION_NAME)
        collection.updateOne(
            Document(Names.ID, id),
            Document("\$set", Document(Names.TITLE, title))
        )
    }

    override suspend fun deleteItem(id: ObjectId) {
        val collection = DefaultMongoDBService.getCollection(Names.COLLECTION_NAME)
        collection.deleteOne(Document(Names.ID, id))
    }

    override suspend fun changeToOld(id: ObjectId) {
        val collection = DefaultMongoDBService.getCollection(Names.COLLECTION_NAME)
        collection.updateOne(
            Document(Names.ID, id),
            Document("\$set", Document(Names.IS_NEW, false))
        )
    }

    override suspend fun changeHighestScore(id: ObjectId, highestScore: String) {
        val collection = DefaultMongoDBService.getCollection(Names.COLLECTION_NAME)
        val oldHighestScore = collection.find(Document(Names.ID, id))
            .first()?.getString(Names.HIGHEST_SCORE)

        val doUpdate = if (oldHighestScore == null) {
            true
        } else {
            val old = oldHighestScore.split('/').first().toInt()
            val new = highestScore.split('/').first().toInt()
            new > old
        }

        if (doUpdate) {
            collection.updateOne(
                Document(Names.ID, id),
                Document("\$set", Document(Names.HIGHEST_SCORE, highestScore))
            )
        }
    }


    override suspend fun getAllPracticeItem(
        userId: ObjectId
    ): List<PracticeItem> {
        val collection = DefaultMongoDBService.getCollection(Names.COLLECTION_NAME)

        val cursor = collection
            .find(Document(Names.USER_ID, userId))
            .projection(
                Document(Names.ID, 1)
                    .append(Names.TITLE, 1)
                    .append(Names.CREATION_DATE, 1)
                    .append(Names.IS_NEW, 1)
                    .append(Names.HIGHEST_SCORE, 1)
            )

        return cursor.toList().map { document ->
            PracticeItem(
                id = document?.get(Names.ID) as ObjectId,
                title = document[Names.TITLE] as String,
                creationDate = document[Names.CREATION_DATE] as Date,
                isNew = document[Names.IS_NEW] as Boolean,
                highestScore = document[Names.HIGHEST_SCORE] as String?
            )
        }.reversed()
    }
}


