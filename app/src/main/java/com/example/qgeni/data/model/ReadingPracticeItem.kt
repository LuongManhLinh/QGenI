package com.example.qgeni.data.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

data class ReadingPracticeItem(
    override val id: Int,
    override val title: String,
    val passage: String,
    val numStatement: String,
    override val creationDate: LocalDate,
    override val isNew: Boolean,
    val questionList: List<McqQuestion>
) : PracticeItem

object MockReadingPracticeItem {

    @RequiresApi(Build.VERSION_CODES.O)
    val readingPracticeItemList: MutableList<ReadingPracticeItem> = mutableListOf()
}
