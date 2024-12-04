package com.example.qgeni.utils

import android.content.Context
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import java.io.File

object AudioGenerator {
    private lateinit var pyObject: PyObject
    private lateinit var cacheDir: File

    fun init(context: Context) {
        val python = Python.getInstance()
        pyObject = python.getModule("text_to_mp3")
        cacheDir = context.cacheDir
    }

    fun generate(text: String, saveName: String) : File {
        val mp3File = File(cacheDir, saveName)
        pyObject.callAttr("text_to_mp3", text, mp3File.absolutePath)
        return mp3File
    }
}