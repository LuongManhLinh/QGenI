package com.example.qgeni.data.preferences

import android.content.Context
import android.content.SharedPreferences

object PortPreferenceManager {
    private const val DB_PORT_KEY = "db_port"
    private const val GEN_PORT_KEY = "gen_port"
    private const val PREFERENCE_NAME = "port_preferences"

    fun loadDbPort(context: Context): Int {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(DB_PORT_KEY, -1)
    }

    fun saveDbPort(context: Context, port: Int) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt(DB_PORT_KEY, port).apply()
    }

    fun loadGenPort(context: Context): Int {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(GEN_PORT_KEY, -1)
    }

    fun saveGenPort(context: Context, port: Int) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt(GEN_PORT_KEY, port).apply()
    }
}