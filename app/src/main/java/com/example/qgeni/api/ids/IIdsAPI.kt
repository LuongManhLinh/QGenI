package com.example.qgeni.api.ids

import android.graphics.Bitmap

interface IIdsAPI {
    suspend fun describe(image: Bitmap) : String?
}