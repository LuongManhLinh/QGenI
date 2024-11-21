package com.example.qgeni.data.model

data class McqQuestion(
    val question: String,
    val answerList: List<String>,
    val correctAnswer: String,
)

object McqMockData {
    val answerList = listOf("A", "B", "C")
    val questions = listOf(
        McqQuestion(
            question = "Choose the correct picture",
            answerList = answerList,
            correctAnswer = "A"
        ),
        McqQuestion(
            question = "Choose the correct picture",
            answerList = answerList,
            correctAnswer = "B"
        ),
        McqQuestion(
            question = "Choose the correct picture",
            answerList = answerList,
            correctAnswer = "C"
        ),
    )
}

object TrueFalseMockData {
    val answerList = listOf("TRUE", "FALSE", "NOT GIVEN")
    val questions = listOf(
        McqQuestion(
            question = "Choose the correct picture",
            answerList = answerList,
            correctAnswer = "TRUE"
        ),
        McqQuestion(
            question = "Choose the correct picture",
            answerList = answerList,
            correctAnswer = "FALSE"
        ),
        McqQuestion(
            question = "Choose the correct picture",
            answerList = answerList,
            correctAnswer = "NOT GIVEN"
        ),
    )
}
