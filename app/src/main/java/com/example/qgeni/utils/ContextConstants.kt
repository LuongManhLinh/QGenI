package com.example.qgeni.utils

import android.content.Context
import java.io.File

object ContextConstants {
    lateinit var cacheDir: File

    fun init(context: Context) {
        cacheDir = context.cacheDir
    }
}