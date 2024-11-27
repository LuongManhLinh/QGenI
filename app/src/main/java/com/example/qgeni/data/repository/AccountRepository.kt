package com.example.qgeni.data.repository

import com.mongodb.client.MongoCollection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
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


class DefaultAccountRepository(
    private val mongoDBService: MongoDBService
) : AccountRepository {

    object Names {
        const val COLLECTION_NAME = "account"
        const val ID = "_id"
        const val USERNAME = "username"
        const val PASSWORD = "password"
    }

    private suspend fun getCollection(
        serverAddress: Pair<String, Int>?
    ): MongoCollection<Document?> {

        val db = if (serverAddress == null) {
            mongoDBService.getDatabaseConnection(
                DefaultConnection.HOST,
                DefaultConnection.PORT,
                DefaultConnection.DB_NAME
            )
        } else {
            mongoDBService.getDatabaseConnection(
                serverAddress.first,
                serverAddress.second,
                DefaultConnection.DB_NAME
            )
        }

        return db.getCollection(Names.COLLECTION_NAME)
    }


    override suspend fun checkExistence(
        username: String,
        password: String,
        serverAddress: Pair<String, Int>?
    ): BsonObjectId? {

        val collection = getCollection(serverAddress)

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

        val collection = getCollection(serverAddress)

        try {
            collection.insertOne(
                Document(Names.USERNAME, username).append(Names.PASSWORD, password)
            )
        } catch (e: Exception) {
            return false
        }

        return true
    }

    suspend fun getAllUser(): List<String> {
        val collection = getCollection(null)
        val result = collection.find()
        val list = mutableListOf<String>()
        for (document in result) {
            if (document != null) {
                list.add(document.getString(Names.USERNAME))
            }
        }
        return list
    }

}


fun main() {
    runBlocking(Dispatchers.IO) {
        val a = DefaultAccountRepository(DefaultMongoDBService)

        val b = a.checkExistence("User1", "1234")


        print(b)


    }


}