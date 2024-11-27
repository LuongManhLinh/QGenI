package com.example.qgeni.data.repository

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.Document

interface MongoDBService {
    suspend fun getDatabaseConnection(host: String, port: Int, dbName: String) : MongoDatabase
}

object DefaultMongoDBService : MongoDBService {

    override suspend fun getDatabaseConnection(
        host: String,
        port: Int,
        dbName: String
    ): MongoDatabase {
        val uri = "mongodb://$host:$port/"
        val mongoClient = MongoClients.create(uri)
        return mongoClient.getDatabase(dbName)
    }

    suspend fun getDefaultDatabaseConnection() : MongoDatabase {
        return getDatabaseConnection(
            DefaultConnection.HOST, DefaultConnection.PORT, DefaultConnection.DB_NAME
        )
    }

    suspend fun getCollection(
        collectionName: String,
        serverAddress: Pair<String, Int>? = null
    ) : MongoCollection<Document?> {
        val db = if (serverAddress == null) {
            getDefaultDatabaseConnection()
        } else {
            getDatabaseConnection(
                serverAddress.first, serverAddress.second, DefaultConnection.DB_NAME
            )
        }
        return db.getCollection(collectionName)
    }
}