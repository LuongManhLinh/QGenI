package com.example.qgeni.data.api.ids

import android.graphics.Bitmap

interface IFullIdsAPI {
    /**
     * @param image: the image to be used as the topic to generate similar images
     * @param numDesiredImage: the number of similar images to be generated
     */
    suspend fun getSimilarImage(image: Bitmap, numDesiredImage: Int) : List<Bitmap>

    /**
     * @param image: the image to be used as the topic to generate questions
     * @param numQuestion: the number of questions to be generated
     * @return: a pair of list of images and list of numQuestion descriptions of numQuestion
     *         first images of the list
     */
    suspend fun createQuestion(image: Bitmap, numQuestion: Int) : Pair<List<Bitmap>, List<String>>
}