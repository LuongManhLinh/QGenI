package com.example.qgeni.data.repository

import com.example.qgeni.data.model.PracticeItem
import com.example.qgeni.data.model.ReadingAnswer
import com.example.qgeni.data.model.ReadingPracticeItem
import com.example.qgeni.data.model.ReadingQuestion
import com.example.qgeni.data.preferences.UserPreferenceManager
import org.bson.Document
import org.bson.types.ObjectId
import java.util.Date


interface ReadingRepository {

    suspend fun insert(
        item: ReadingPracticeItem
    )

    suspend fun getItem(
        id: ObjectId
    ): ReadingPracticeItem

    suspend fun changeTitle(
        id: ObjectId,
        title: String
    )
}


object DefaultReadingRepository : ReadingRepository, PracticeRepository {
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
    }


    override suspend fun insert(
        item: ReadingPracticeItem
    ) {
        val userId = UserPreferenceManager.getUserId()

        val document = Document(
            Names.USER_ID, userId
        ).append(
            Names.TITLE, item.title
        ).append(
            Names.CREATION_DATE, item.creationDate
        ).append(
            Names.PASSAGE, item.passage
        ).append(
            Names.QUESTIONS, item.questionList.map {
                Document(
                    Names.Q_STATEMENT, it.statement
                ).append(
                    Names.Q_ANSWER, it.answer.toInt()
                )
            }
        ).append(
            Names.IS_NEW, item.isNew
        )

        val collection = DefaultMongoDBService.getCollection(Names.COLLECTION_NAME)

        collection.insertOne(document)
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
            },
            isNew = document[Names.IS_NEW] as Boolean
        )
    }


    override suspend fun changeTitle(id: ObjectId, title: String) {
        val collection = DefaultMongoDBService.getCollection(Names.COLLECTION_NAME)
        collection.updateOne(
            Document(Names.ID, id),
            Document("\$set", Document(Names.TITLE, title))
        )
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
            )

        return cursor.toList().map { document ->
            PracticeItem(
                id = document?.get(Names.ID) as ObjectId,
                title = document[Names.TITLE] as String,
                creationDate = document[Names.CREATION_DATE] as Date,
                isNew = document[Names.IS_NEW] as Boolean
            )
        }
    }
}


