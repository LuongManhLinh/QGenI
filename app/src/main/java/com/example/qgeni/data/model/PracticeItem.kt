package com.example.qgeni.data.model

import org.bson.types.ObjectId
import java.util.Date

open class PracticeItem(
    open val id: ObjectId,
    open val title: String,
    open val creationDate: Date,
    open val isNew: Boolean,
    open val highestScore: String?
)

