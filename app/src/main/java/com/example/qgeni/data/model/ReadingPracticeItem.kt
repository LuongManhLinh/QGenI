package com.example.qgeni.data.model

import org.bson.types.ObjectId
import java.util.Date



data class ReadingPracticeItem(
    override val id: ObjectId,
    override val title: String,
    val passage: String,
    override val creationDate: Date,
    override val isNew: Boolean,
    override val highestScore: String?,
    val questionList: List<ReadingQuestion>
) : PracticeItem(id, title, creationDate, isNew, highestScore)



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
    };

    fun toInt(): Int {
        return when (this) {
            TRUE -> 1
            FALSE -> -1
            NOT_GIVEN -> 0
        }
    }

    companion object {
        fun fromInt(value: Int): ReadingAnswer {
            return when (value) {
                1 -> TRUE
                -1 -> FALSE
                0 -> NOT_GIVEN
                else -> throw IllegalArgumentException("Invalid value: $value")
            }
        }
    }
}

