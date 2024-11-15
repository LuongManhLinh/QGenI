package com.example.qgeni.api.ids

import android.graphics.Bitmap

interface IFullIdsAPI {
    suspend fun getSimilarImage(image: Bitmap, numDesiredImage: Int) : List<Bitmap>
}