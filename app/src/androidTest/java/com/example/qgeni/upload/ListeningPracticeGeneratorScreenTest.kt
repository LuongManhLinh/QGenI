package com.example.qgeni.upload

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.qgeni.ui.screens.uploads.ListeningPracticeGeneratorScreen
import com.example.qgeni.ui.screens.uploads.ListeningPracticeGeneratorViewModel
import com.example.qgeni.ui.theme.QGenITheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListeningPracticeGeneratorScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testScreenDisplaysTitleAndButtonWhenEmpty() {
        // Arrange
        composeTestRule.setContent {
            QGenITheme {
                ListeningPracticeGeneratorScreen(
                    onBackClick = {},
                    onLeaveButtonClick = {},
                )
            }
        }

        // Assert
        composeTestRule.onNodeWithContentDescription("BackIcon").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tạo đề nghe").assertIsDisplayed()
        composeTestRule.onNodeWithText("TẢI TỆP").assertIsDisplayed()
    }

    @Test
    fun testUploadFileDialogDisplayed() {
        composeTestRule.setContent {
            QGenITheme {
                ListeningPracticeGeneratorScreen(
                    onBackClick = {},
                    onLeaveButtonClick = {},
                )
            }
        }
        composeTestRule.onNodeWithTag("upload file").performClick()
        composeTestRule.onNodeWithText("JPEG, PNG, up to 50MB").assertIsDisplayed()
    }

    @Test
    fun testNextButtonIsClickable() {
        composeTestRule.setContent {
            QGenITheme {
                ListeningPracticeGeneratorScreen(
                    onBackClick = {},
                    onLeaveButtonClick = {},
                )
            }
        }
        composeTestRule.onNodeWithTag("next_button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("next_button").performClick()
    }

    @Test
    fun testAll() {
        composeTestRule.setContent {
            QGenITheme {
                ListeningPracticeGeneratorScreen(
                    onBackClick = {},
                    onLeaveButtonClick = {},
                )
            }
        }
        composeTestRule.onNodeWithContentDescription("BackIcon").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tạo đề nghe").assertIsDisplayed()
        composeTestRule.onNodeWithText("TẢI TỆP").assertIsDisplayed()

        composeTestRule.onNodeWithTag("upload file").performClick()
        composeTestRule.onNodeWithText("JPEG, PNG, up to 50MB").assertIsDisplayed()

        composeTestRule.onNodeWithTag("next_button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("next_button").performClick()
    }

}


