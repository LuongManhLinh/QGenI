package com.example.qgeni.data.repository

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase

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
}