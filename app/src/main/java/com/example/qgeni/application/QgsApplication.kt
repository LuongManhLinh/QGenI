package com.example.qgeni.application

import android.util.Log
import com.example.qgeni.data.api.qgs.QgsGeminiAPI
import com.example.qgeni.data.api.qgs.QgsHostChecker
import org.bson.types.ObjectId

object QgsApplication {
    suspend fun getReadingPracticeItem(
        paragraph: String,
        numQuestion: Int
    ) : ObjectId? {
        try {
            val questionList = QgsGeminiAPI.generateQuestions(paragraph, numQuestion)

            return QgsHostChecker.validate(paragraph, questionList)
        }
        catch (e: Exception) {
            Log.e("IdsApplication", Log.getStackTraceString(e))
            return null
        }
    }
}