package com.example.qgeni.data.model

import java.time.LocalDate

interface PracticeItem {
    val id: Int
    val title: String
    val creationDate: LocalDate
    val isNew: Boolean
}
