package com.example.qgeni.data.api

object RequestType {
    const val TFN_CHECK = 1
    const val IMG_FIND_SIMILAR_ONLY = 3
}

object ResponseType {
    const val ACCEPTED = 0
    const val SUCCESS = 1
    const val INVALID_REQUEST = 2
    const val SERVER_ERROR = 3
    const val CLIENT_ERROR = 4
    const val SERVER_BUSY = 5
}
