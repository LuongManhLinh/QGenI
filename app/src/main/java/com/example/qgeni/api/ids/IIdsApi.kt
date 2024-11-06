package com.example.qgeni.api.ids

import android.graphics.Bitmap

interface IIdsApi {
    fun describe(image: Bitmap) : String?
}