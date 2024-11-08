package com.example.qgeni.api.qgs

data class QgsForm(
    val statement: String,
    val answer: QgsAnswer
) {
}

enum class QgsAnswer {
    TRUE,
    FALSE,
    NOT_GIVEN
}