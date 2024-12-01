package com.example.qgeni.data.repository

import com.example.qgeni.data.DefaultConnection
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

    private suspend fun getDefaultDatabaseConnection() : MongoDatabase {
        return getDatabaseConnection(
            DefaultConnection.HOST, DefaultConnection.dbPort, DefaultConnection.DB_NAME
        )
    }

    suspend fun getCollection(
        collectionName: String
    ) : MongoCollection<Document?> {
        val db = getDefaultDatabaseConnection()
        return db.getCollection(collectionName)
    }
}