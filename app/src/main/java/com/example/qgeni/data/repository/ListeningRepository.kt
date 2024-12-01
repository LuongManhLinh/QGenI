package com.example.qgeni.data.repository


import android.util.Log
import com.example.qgeni.data.api.CommunicationUtils
import com.example.qgeni.data.model.ListeningPracticeItem
import com.example.qgeni.data.model.ListeningQuestion
import com.example.qgeni.data.model.PracticeItem
import com.example.qgeni.data.preferences.UserPreferenceManager
import org.bson.Document
import org.bson.types.Binary
import org.bson.types.ObjectId
import java.util.Date



interface ListeningRepository {
    suspend fun insert(
        item: ListeningPracticeItem
    )

    suspend fun getItem(
        id: ObjectId
    ): ListeningPracticeItem

    suspend fun changeTitle(
        id: ObjectId,
        title: String
    )
}

object DefaultListeningRepository : ListeningRepository, PracticeRepository {
    object Names {
        const val COLLECTION_NAME = "listening"
        const val ID = "_id"
        const val USER_ID = "userId"
        const val TITLE = "title"
        const val CREATION_DATE = "creationDate"
        const val QUESTIONS = "questions"
        const val Q_IMAGE_BYTE_ARRAYS = "imageByteArrays"
        const val Q_DESCRIPTION = "description"
        const val Q_ANSWER_INDEX = "answerIndex"
        const val IS_NEW = "isNew"
    }


    override suspend fun insert(
        item: ListeningPracticeItem
    ) {
        val userId = UserPreferenceManager.getUserId()
        val document = Document(
            Names.USER_ID, userId
        ).append(
            Names.TITLE, item.title
        ).append(
            Names.CREATION_DATE, item.creationDate
        ).append(
            Names.QUESTIONS, item.questionList.map {
                Document(
                    Names.Q_IMAGE_BYTE_ARRAYS, it.imageList.map { img ->
                        CommunicationUtils.encodeImage(img)
                    }
                ).append(
                    Names.Q_DESCRIPTION, it.description
                ).append(
                    Names.Q_ANSWER_INDEX, it.answerIndex
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
    ): ListeningPracticeItem {
        val collection = DefaultMongoDBService.getCollection(Names.COLLECTION_NAME)
        val cursor = collection.find(Document(Names.ID, id))
        val document = cursor.first()
        return ListeningPracticeItem(
                id = document?.get(Names.ID) as ObjectId,
                title = document[Names.TITLE] as String,
                creationDate = document.getDate(Names.CREATION_DATE) as Date,
                isNew = document.getBoolean(Names.IS_NEW) as Boolean,
                questionList = document.getList(Names.QUESTIONS, Document::class.java).map { q ->
                    ListeningQuestion(
                        imageList = q.getList(Names.Q_IMAGE_BYTE_ARRAYS, Binary::class.java).map { img ->
                            CommunicationUtils.decodeImage(img.data) ?: throw Exception("Cannot decode image")
                        },
                        description = q.getString(Names.Q_DESCRIPTION),
                        answerIndex = q.getInteger(Names.Q_ANSWER_INDEX)
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
        Log.e("ListeningRepository", "getAllPracticeItem: ${cursor.toList().size}")
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