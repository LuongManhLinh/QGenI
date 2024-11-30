package com.example.qgeni.data.api

object RequestType {
    const val TFN = 1
    const val IMG_DESC_ONLY = 2
    const val IMG_FIND_SIMILAR_ONLY = 3
    const val IMG_FIND_AND_DESC = 4
}

object ResponseType {
    const val SUCCESS = 1
    const val INVALID_REQUEST = 2
    const val SERVER_ERROR = 3
    const val CLIENT_ERROR = 4
}
