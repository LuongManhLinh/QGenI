package com.example.qgeni.application

import android.util.Log
import com.example.qgeni.data.api.qgs.QgsGeminiAPI
import com.example.qgeni.data.api.qgs.QgsHostChecker
import org.bson.types.ObjectId

object QgsApplication {
    private var stopped = false

    suspend fun getReadingPracticeItem(
        paragraph: String,
        numQuestion: Int
    ) : ObjectId? {
        stopped = false
        try {
            val questionList = QgsGeminiAPI.generateQuestions(paragraph, numQuestion)
            if (stopped) {
                return null
            }
            return QgsHostChecker.validate(paragraph, questionList)
        }
        catch (e: Exception) {
            Log.e("IdsApplication", Log.getStackTraceString(e))
            return null
        }
    }

    fun stop() {
        stopped = true
        QgsHostChecker.stop()
    }
}