package com.example.qgeni.data.api.ids

import android.graphics.Bitmap
import org.bson.types.ObjectId

interface IIdsAPI {
    /**
     * @param topicImageList: the list of image to be used as the topic to generate similar images
     * @return: if successfully create the item, return true otherwise return false
     */
    fun createListeningPracticeItem(topicImageList: List<Bitmap>) : ObjectId?
}