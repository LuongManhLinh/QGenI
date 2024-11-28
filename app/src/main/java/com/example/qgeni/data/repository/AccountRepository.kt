package com.example.qgeni.data.repository

import kotlinx.coroutines.runBlocking
import org.bson.BsonObjectId
import org.bson.Document
import org.bson.types.ObjectId


interface AccountRepository {
    suspend fun checkExistence(
        usernameOrEmailOrPhone: String,
        password: String,
        serverAddress: Pair<String, Int>? = null
    ): ObjectId?

    suspend fun createAccount(
        username: String,
        phoneNumber: String? = null,
        email: String? = null,
        password: String,
        serverAddress: Pair<String, Int>? = null
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
        password: String,
        serverAddress: Pair<String, Int>?
    ): ObjectId? {

        val collection = DefaultMongoDBService.getCollection(Names.COLLECTION_NAME, serverAddress)

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
        password: String,
        serverAddress: Pair<String, Int>?
    ) {

        val collection = DefaultMongoDBService.getCollection(Names.COLLECTION_NAME, serverAddress)

        collection.insertOne(
            Document(Names.USERNAME, username)
                .append(Names.PASSWORD, password)
                .append(Names.EMAIL, email)
                .append(Names.PHONE_NUMBER, phoneNumber)
        )

    }

}