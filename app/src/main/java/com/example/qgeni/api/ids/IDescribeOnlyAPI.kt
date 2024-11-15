package com.example.qgeni.api.ids

import android.graphics.Bitmap

interface IDescribeOnlyAPI {
    suspend fun describe(image: Bitmap) : String?
}