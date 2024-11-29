package com.example.qgeni.data.model

import org.bson.types.ObjectId
import java.util.Date



data class ReadingPracticeItem(
    override val id: ObjectId,
    override val title: String,
    val passage: String,
    override val creationDate: Date,
    override val isNew: Boolean,
    val questionList: List<ReadingQuestion>
) : PracticeItem(id, title, creationDate, isNew)



data class ReadingQuestion(
    val statement: String,
    val answer: ReadingAnswer
)



enum class ReadingAnswer {
    TRUE {
        override fun toString(): String {
            return "TRUE"
        }
    },

    FALSE {
        override fun toString(): String {
            return "FALSE"
        }
    },

    NOT_GIVEN {
        override fun toString(): String {
            return "NOT GIVEN"
        }
    }
}

