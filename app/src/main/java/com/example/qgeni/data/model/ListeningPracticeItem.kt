package com.example.qgeni.data.model

import android.media.AudioRecord
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.qgeni.R
import java.time.LocalDate


data class ListeningPracticeItem(
    override val id: Int,
    override val title: String,
    override val creationDate: LocalDate,
    override val isNew: Boolean,
    val record: List<String>,
    val imageList: List<List<ImageItem>>,
    val questionList: List<McqQuestion>
) : PracticeItem

data class ImageItem(
    val label: String,
    val imageRes: Int
)

object MockListeningPracticeItem {
    @RequiresApi(Build.VERSION_CODES.O)
    val listeningPracticeItem = ListeningPracticeItem(
        id = 0,
        title = "Capybara",
        creationDate = LocalDate.now(),
        isNew = true,
        record = listOf("null"),
        imageList = ImageSelection.imageSelection, // Thay đổi để tương thích kiểu mới
        questionList = McqMockData.questions
    )

    @RequiresApi(Build.VERSION_CODES.O)
    val listeningPracticeItemList = List(8) { index ->
        ListeningPracticeItem(
            id = index,
            title = "Bài nghe ${index + 1}",
            creationDate = LocalDate.now().minusDays(index.toLong()), // Ngày lùi dần
            isNew = index < 3, // Đánh dấu "NEW" cho 3 item đầu
            record = listOf("null"), // Danh sách record giả
            imageList = ImageSelection.imageSelection, // Danh sách hình ảnh giả
            questionList = McqMockData.questions // Danh sách câu hỏi giả
        )
    }
}

object ImageSelection {
    val imageSelection = listOf(
            ImageData.imageList,
            ImageData.imageList2,
            ImageData.imageList3
    )
}

object ImageData {
    val imageList = listOf(
        ImageItem("Pic. A", R.drawable.avatar),
        ImageItem("Pic. B", R.drawable.avatar),
        ImageItem("Pic. C", R.drawable.avatar)
    )
    val imageList2 = listOf(
        ImageItem("Pic. A", R.drawable.avatar),
        ImageItem("Pic. B", R.drawable.reading),
        ImageItem("Pic. C", R.drawable.backpack1)
    )
    val imageList3 = listOf(
        ImageItem("Pic. A", R.drawable.backpack1),
        ImageItem("Pic. B", R.drawable.backpack1),
        ImageItem("Pic. C", R.drawable.backpack1)
    )
}