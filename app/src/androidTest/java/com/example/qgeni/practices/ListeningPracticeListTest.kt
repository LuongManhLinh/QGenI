package com.example.qgeni.practices

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.qgeni.data.model.PracticeItem
import com.example.qgeni.ui.screens.practices.ListeningPracticeListScreen
import com.example.qgeni.ui.screens.practices.ListeningPracticeListViewModel
import com.example.qgeni.ui.screens.practices.PracticeListUIState
import com.example.qgeni.ui.theme.QGenITheme
import kotlinx.coroutines.flow.MutableStateFlow
import org.bson.types.ObjectId
import org.junit.Rule
import org.junit.Test
import java.util.Date

class ListeningPracticeList {

    @get:Rule
    val composeTestRule = createComposeRule()

    val mockData = generateMockPracticeItems()

    // Mock ViewModel để trả về dữ liệu giả
    private val mockViewModel = object : ListeningPracticeListViewModel() {
        override val practiceListUIState = MutableStateFlow(
            PracticeListUIState(
                practiceItemList = mockData,
                showDeleteDialog = false,
                showOpenDialog = false,
                selectedItemId = null
            )
        )
        override suspend fun getPracticeItemList(): List<PracticeItem> {
            return mockData
        }
    }
    @Test
    fun testListeningPracticeListScreen(){
        // Set nội dung của màn hình
        composeTestRule.setContent {
            QGenITheme {
                ListeningPracticeListScreen(
                    onBackClick = {},
                    onItemClick = {},
                    viewModel = mockViewModel
                )
            }
        }
        composeTestRule.onNodeWithContentDescription("BackIcon").assertIsDisplayed()
        composeTestRule.onNodeWithText("Ngăn đề nghe").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("backpack1").assertIsDisplayed()

        composeTestRule.onNodeWithText("Bài nghe 1").assertIsDisplayed()

        composeTestRule.onNodeWithTag("open dialog").performClick()


    }
}


fun generateMockPracticeItems(): List<PracticeItem> {
    return listOf(
        PracticeItem(
            id = ObjectId(),
            title = "Bài nghe 1",
            creationDate = Date(),
            isNew = true
        )
    )
}