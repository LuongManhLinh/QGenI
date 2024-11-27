package com.example.qgeni.data.repository

import android.graphics.Bitmap
import com.example.qgeni.api.CommunicationUtils
import org.bson.Document
import org.bson.types.Binary
import org.bson.types.ObjectId
import java.util.Date

data class ListeningItem(
    val userId: ObjectId,
    val title: String,
    val creationDate: Date,
    val images: List<Bitmap>,
    val answers: List<String>,
    val isNew: Boolean
)

interface ListeningRepository {
    suspend fun insert(
        item: ListeningItem,
        serverAddress: Pair<String, Int>? = null
    )

    suspend fun getAll(
        userId: ObjectId,
        serverAddress: Pair<String, Int>? = null
    ): List<ListeningItem>

}

object DefaultListeningRepository : ListeningRepository {
    object Names {
        const val COLLECTION_NAME = "listening"
        const val USER_ID = "userId"
        const val TITLE = "title"
        const val CREATION_DATE = "creationDate"
        const val IMAGE_BYTE_ARRAYS = "imageByteArrays"
        const val ANSWERS = "answers"
        const val IS_NEW = "isNew"
    }

    override suspend fun insert(
        item: ListeningItem,
        serverAddress: Pair<String, Int>?
    ) {
        val document = Document(
            Names.USER_ID, item.userId
        ).append(
            Names.TITLE, item.title
        ).append(
            Names.CREATION_DATE, item.creationDate
        ).append(
            Names.IMAGE_BYTE_ARRAYS, item.images.map { CommunicationUtils.encodeImage(it) }
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
    ): List<ListeningItem> {
        val collection = DefaultMongoDBService.getCollection(Names.COLLECTION_NAME, serverAddress)
        val result = collection.find(Document(Names.USER_ID, userId))

        return result.toList().map {
            ListeningItem(
                userId = it?.get(Names.USER_ID) as ObjectId,
                title = it[Names.TITLE] as String,
                creationDate = it[Names.CREATION_DATE] as Date,
                images = (it[Names.IMAGE_BYTE_ARRAYS] as List<Binary>).map { imgBinary ->
                    val imgByteArray = imgBinary.data
                    CommunicationUtils.decodeImage(imgByteArray) ?: throw IllegalArgumentException("Cannot decode image")
                },
                answers = it[Names.ANSWERS] as List<String>,
                isNew = it[Names.IS_NEW] as Boolean
            )
        }
    }
}