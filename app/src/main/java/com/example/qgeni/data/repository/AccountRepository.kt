package com.example.qgeni.data.repository

import com.example.qgeni.utils.ContextConstants
import org.bson.Document
import org.bson.types.ObjectId


interface AccountRepository {
    suspend fun checkExistence(
        usernameOrEmailOrPhone: String,
        password: String
    ): ObjectId?

    suspend fun createAccount(
        username: String,
        phoneNumber: String? = null,
        email: String? = null,
        password: String
    )

    suspend fun getUserInfo(userId: ObjectId): Document?

    suspend fun updateUserInfo(
        userId: ObjectId,
        username: String? = null,
        phoneNumber: String? = null,
        email: String? = null
    ): Boolean

    suspend fun checkEmail(
        email: String
    ): Boolean

    suspend fun resetPassword(
        newPassword: String
    )
}


object DefaultAccountRepository : AccountRepository {

    object Names {
        const val COLLECTION_NAME = "account"
        const val ID = "_id"
        const val USERNAME = "username"
        const val PHONE_NUMBER = "phoneNumber"
        const val EMAIL = "email"
        const val PASSWORD = "password"
    }

    override suspend fun checkExistence(
        usernameOrEmailOrPhone: String,
        password: String
    ): ObjectId? {

        val collection = DefaultMongoDBService.getCollection(Names.COLLECTION_NAME)

        val query = Document(
            "\$and", listOf(
                Document(
                    "\$or", listOf(
                        Document(Names.USERNAME, usernameOrEmailOrPhone),
                        Document(Names.EMAIL, usernameOrEmailOrPhone),
                        Document(Names.PHONE_NUMBER, usernameOrEmailOrPhone)
                    )
                ),
                Document(Names.PASSWORD, password)
            )
        )

        val result = collection.find(
            query
        )

        return result.first()?.get(Names.ID) as ObjectId?
    }


    override suspend fun createAccount(
        username: String,
        phoneNumber: String?,
        email: String?,
        password: String
    ) {

        val collection = DefaultMongoDBService.getCollection(Names.COLLECTION_NAME)

        collection.insertOne(
            Document(Names.USERNAME, username)
                .append(Names.PASSWORD, password)
                .append(Names.EMAIL, email)
                .append(Names.PHONE_NUMBER, phoneNumber)
        )

    }

    override suspend fun getUserInfo(userId: ObjectId): Document? {
        val collection = DefaultMongoDBService.getCollection(Names.COLLECTION_NAME)

        val query = Document(Names.ID, userId)
        return collection.find(query).firstOrNull()
    }

    override suspend fun updateUserInfo(
        userId: ObjectId,
        username: String?,
        phoneNumber: String?,
        email: String?
    ): Boolean {
        val collection = DefaultMongoDBService.getCollection(Names.COLLECTION_NAME)

        val updateFields = mutableMapOf<String, Any>()
        username?.let { updateFields[Names.USERNAME] = it }
        phoneNumber?.let { updateFields[Names.PHONE_NUMBER] = it }
        email?.let { updateFields[Names.EMAIL] = it }

        if (updateFields.isEmpty()) return false

        val updateDoc = Document("\$set", updateFields)

        val result = collection.updateOne(
            Document(Names.ID, userId),
            updateDoc
        )

        return result.modifiedCount > 0
    }

    override suspend fun checkEmail(email: String) : Boolean {
        val collection = DefaultMongoDBService.getCollection(Names.COLLECTION_NAME)

        val query = Document(Names.EMAIL, email)
        val result = collection.find(query)

        return result.firstOrNull() != null
    }

    override suspend fun resetPassword(newPassword: String) {
        val collection = DefaultMongoDBService.getCollection(Names.COLLECTION_NAME)

        val email = ContextConstants.getEmailForReset()
        val query = Document(Names.EMAIL, email)
        val updateDoc = Document("\$set", Document(Names.PASSWORD, newPassword))

        collection.updateOne(
            query,
            updateDoc
        )
    }

}