package com.example.qgeni.data.preferences

import android.content.Context
import android.content.SharedPreferences

object PortPreferenceManager {
    private const val HOST_KEY = "host"
    private const val DB_PORT_KEY = "db_port"
    private const val GEN_PORT_KEY = "gen_port"
    private const val CTRL_PORT_KEY = "ctrl_port"
    private const val PREFERENCE_NAME = "port_preferences"

    fun loadHost(context: Context): String {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(HOST_KEY, "localhost") ?: "localhost"
    }

    fun saveHost(context: Context, host: String) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(HOST_KEY, host).apply()
    }

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

    fun loadCtrlPort(context: Context): Int {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(CTRL_PORT_KEY, -1)
    }

    fun saveCtrlPort(context: Context, port: Int) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt(CTRL_PORT_KEY, port).apply()
    }
}