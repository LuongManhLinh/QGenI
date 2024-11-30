package com.example.qgeni.application

import android.graphics.Bitmap
import android.util.Log
import com.example.qgeni.data.api.qgs.QgsGeminiAPI
import com.example.qgeni.data.model.ListeningPracticeItem
import com.example.qgeni.data.model.ReadingPracticeItem
import com.example.qgeni.data.model.ReadingQuestion
import org.bson.types.ObjectId
import java.util.Date

object QgsApplication {
    suspend fun getReadingPracticeItem(
        paragraph: String,
        numQuestion: Int
    ) : ReadingPracticeItem? {
        try {
            val questionList = QgsGeminiAPI.generateQuestions(paragraph, numQuestion)

            return ReadingPracticeItem(
                id = ObjectId(),
                title = "",
                passage = paragraph,
                creationDate = Date(),
                isNew = true,
                questionList = questionList
            )
        }
        catch (e: Exception) {
            Log.e("IdsApplication", Log.getStackTraceString(e))
            return null
        }
    }
}