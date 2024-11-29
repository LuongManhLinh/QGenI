package com.example.qgeni.application

import android.graphics.Bitmap
import android.util.Log
import com.example.qgeni.api.ids.IdsGeminiAPI
import com.example.qgeni.api.ids.IdsHostAPI
import com.example.qgeni.data.model.ListeningPracticeItem
import com.example.qgeni.data.model.ListeningQuestion
import org.bson.types.ObjectId
import java.util.Date

object IdsApplication {
    suspend fun getListeningPracticeItem(
        image: Bitmap,
        numQuestion: Int
    ) : ListeningPracticeItem? {

        try {
            var imageList = IdsHostAPI.getSimilarImage(image, numQuestion * 4 - 1)

            imageList = imageList.toMutableList()
            imageList.add(image)
            imageList.shuffled()

            val descImageList = imageList.subList(0, numQuestion)
            val unDescImageList = imageList.subList(numQuestion, imageList.size)

            val descList = IdsGeminiAPI.describeMany(descImageList)

            val questionList = mutableListOf<ListeningQuestion>()

            for (i in 0 until numQuestion) {

                var imageForQuestion = unDescImageList.subList(4 * i, 4 * i + 4)
                val indexToAdd = (0 until 4).random()
                imageForQuestion = imageForQuestion.toMutableList()
                imageForQuestion.add(indexToAdd, descImageList[i])

                questionList.add(
                    ListeningQuestion(
                        imageList = imageForQuestion,
                        description = descList[i],
                        answerIndex = i
                    )
                )
            }

            return ListeningPracticeItem(
                id = ObjectId(),
                title = "",
                creationDate = Date(),
                isNew = true,
                questionList = questionList
            )
        } catch (e: Exception) {
            Log.e("IdsApplication", Log.getStackTraceString(e))
            return null
        }
    }
}