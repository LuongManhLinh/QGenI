package com.example.qgeni.practices

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.qgeni.data.model.PracticeItem
import com.example.qgeni.ui.screens.practices.ListeningPracticeListViewModel
import com.example.qgeni.ui.screens.practices.PracticeListUIState
import com.example.qgeni.ui.screens.practices.ReadingPracticeListScreen
import com.example.qgeni.ui.screens.practices.ReadingPracticeListViewModel
import com.example.qgeni.ui.theme.QGenITheme
import kotlinx.coroutines.flow.MutableStateFlow
import org.bson.types.ObjectId
import org.junit.Rule
import org.junit.Test
import java.util.Date

class ReadingPracticeListTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    val mockData = generateReading()

    // Mock ViewModel để trả về dữ liệu giả
    private val mockViewModel = object : ReadingPracticeListViewModel() {
        override val practiceListUIState = MutableStateFlow(
            PracticeListUIState(
                practiceItemList = mockData,
                showDeleteDialog = false,
                showOpenDialog = false,
                selectedIdx = null
            )
        )
        override suspend fun getPracticeItemList(): List<PracticeItem> {
            return mockData
        }
    }
    @Test
    fun testReadingPracticeListScreen() {
        // Set nội dung của màn hình
        composeTestRule.setContent {
            QGenITheme {
                ReadingPracticeListScreen(
                    viewModel = mockViewModel,
                    onBackClick = {},
                    onItemClick = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("BackIcon").assertIsDisplayed()

        composeTestRule.onNodeWithText("Ngăn đề đọc").assertIsDisplayed()
        // Kiểm tra rằng ảnh nền cũng hiển thị
        composeTestRule.onNodeWithContentDescription("backpack1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Bài đọc 1").assertIsDisplayed()

        composeTestRule.onNodeWithTag("show Dialog").performClick()
    }
}

fun generateReading(): List<PracticeItem> {
    return listOf(
        PracticeItem(
            id = ObjectId(),
            title = "Bài đọc 1",
            creationDate = Date(),
            isNew = true,
            highestScore = ""
        )
    )
}