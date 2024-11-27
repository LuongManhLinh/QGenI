package com.example.qgeni.data.repository

import org.bson.BsonObjectId
import org.bson.Document


interface AccountRepository {
    suspend fun checkExistence(
        username: String,
        password: String,
        serverAddress: Pair<String, Int>? = null
    ): BsonObjectId?

    suspend fun createAccount(
        username: String,
        password: String,
        serverAddress: Pair<String, Int>? = null
    ): Boolean
}


object DefaultAccountRepository : AccountRepository {

    object Names {
        const val COLLECTION_NAME = "account"
        const val ID = "_id"
        const val USERNAME = "username"
        const val PASSWORD = "password"
    }

    override suspend fun checkExistence(
        username: String,
        password: String,
        serverAddress: Pair<String, Int>?
    ): BsonObjectId? {

        val collection = DefaultMongoDBService.getCollection(Names.COLLECTION_NAME, serverAddress)

        val result = collection.find(
            Document(Names.USERNAME, username).append(Names.PASSWORD, password)
        )

        return result.first()?.get(Names.ID) as BsonObjectId?
    }


    override suspend fun createAccount(
        username: String,
        password: String,
        serverAddress: Pair<String, Int>?
    ): Boolean {

        val collection = DefaultMongoDBService.getCollection(Names.COLLECTION_NAME, serverAddress)

        try {
            collection.insertOne(
                Document(Names.USERNAME, username).append(Names.PASSWORD, password)
            )
        } catch (e: Exception) {
            return false
        }

        return true
    }

}
