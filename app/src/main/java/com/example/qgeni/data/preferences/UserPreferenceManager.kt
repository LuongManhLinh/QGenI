package com.example.qgeni.data.preferences

import android.content.Context
import android.content.SharedPreferences
import org.bson.types.ObjectId

object UserPreferenceManager {

    private const val ID_KEY = "user_id"
    private const val PREFERENCE_NAME = "id_preferences"

    private var userId: ObjectId? = null

    fun loadUserId(context: Context) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

        this.userId = sharedPreferences.getString(ID_KEY, null)?.let {
            ObjectId(it)
        }
    }

    fun getUserId(): ObjectId? {
        return this.userId
    }

    fun saveUserId(context: Context, userId: ObjectId) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(ID_KEY, userId.toHexString()).apply()
        this.userId = userId
    }

    fun removeUserId(context: Context) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().remove(ID_KEY).apply()
        this.userId = null
    }

}