package com.example.qgeni.data.model

import android.graphics.Bitmap
import org.bson.types.ObjectId
import java.io.File
import java.util.Date


data class ListeningPracticeItem(
    override val id: ObjectId,
    override val title: String,
    override val creationDate: Date,
    override val isNew: Boolean,
    val questionList: List<ListeningQuestion>
) : PracticeItem(id, title, creationDate, isNew)


/**
 *  @property imageList: danh sách hình ảnh cho 1 câu hỏi
 *  @property description: mô tả cho hình ảnh được chọn
 *  @property answerIndex: vị trí của hình ảnh được mô tả trong imageList
 *  @property mp3File: file mp3 chứa mô tả của hình ảnh
 */
data class ListeningQuestion(
    val imageList: List<Bitmap>,
    val description: String,
    val answerIndex: Int,
    val mp3File: File
)
