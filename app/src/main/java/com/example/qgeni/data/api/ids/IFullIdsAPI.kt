package com.example.qgeni.data.api.ids

import android.graphics.Bitmap
import org.bson.types.ObjectId

interface IFullIdsAPI {
    companion object {
        const val QUESTION_PER_TOPIC = 4
        const val IMG_PER_QUESTION = 4
    }

    /**
     * @param topicImageList: the list of image to be used as the topic to generate similar images
     * @return: if successfully create the item, return true otherwise return false
     */
    fun createListeningPracticeItem(topicImageList: List<Bitmap>) : ObjectId?
}