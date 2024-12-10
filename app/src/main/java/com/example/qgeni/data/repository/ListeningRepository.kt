package com.example.qgeni.data.repository

import android.util.Log
import com.example.qgeni.data.api.CommunicationUtils
import com.example.qgeni.data.model.ListeningPracticeItem
import com.example.qgeni.data.model.ListeningQuestion
import com.example.qgeni.data.model.PracticeItem
import org.bson.Document
import org.bson.types.Binary
import org.bson.types.ObjectId
import java.util.Date



interface ListeningRepository {
    suspend fun getItem(
        id: ObjectId
    ): ListeningPracticeItem
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
        const val Q_MP3_BYTE_ARRAY = "mp3ByteArray"
        const val IS_NEW = "isNew"
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
                questionList = document.getList(Names.QUESTIONS, Document::class.java).mapIndexed{ idx, q ->
                    ListeningQuestion(
                        imageList = q.getList(Names.Q_IMAGE_BYTE_ARRAYS, Binary::class.java).map { img ->
                            CommunicationUtils.decodeImage(img.data) ?: throw Exception("Cannot decode image")
                        },
                        description = q.getString(Names.Q_DESCRIPTION),
                        answerIndex = q.getInteger(Names.Q_ANSWER_INDEX),
                        mp3File = CommunicationUtils.decodeMp3(
                            mp3Bytes = (q[Names.Q_MP3_BYTE_ARRAY] as Binary).data,
                            saveName = "${id.toHexString()}_desc_$idx.mp3"
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