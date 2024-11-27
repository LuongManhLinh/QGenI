package com.example.qgeni.application

import android.graphics.Bitmap
import com.example.qgeni.api.ids.IdsGeminiAPI
import com.example.qgeni.api.ids.IdsHostAPI

object IdsApplication {
    suspend fun getQuestions(image: Bitmap, numQuestion: Int): List<Pair<Bitmap, String>> {
        val pair = IdsHostAPI.createQuestion(image, numQuestion)
        val imgList = pair.first
        val descList = pair.second.toMutableList()

        for (idx in 0 until imgList.size - descList.size) {
            descList.add("")
        }

        return imgList.zip(descList)
    }

    suspend fun getSimilarAndDescribe(image: Bitmap, numQuestion: Int): List<Pair<Bitmap, String>> {
        var imgList = IdsHostAPI.getSimilarImage(image, numQuestion * 4 - 1)

        imgList = imgList.toMutableList()
        imgList.add(image)
        imgList = imgList.shuffled()

        val descList = IdsGeminiAPI.describeMany(imgList.subList(0, numQuestion)).toMutableList()
        for (idx in 0 until imgList.size - descList.size) {
            descList.add("---")
        }
        return imgList.zip(descList)
    }
}