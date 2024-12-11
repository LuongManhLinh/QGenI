package com.example.qgeni.upload

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import com.example.qgeni.ui.screens.uploads.PasteTextField
import com.example.qgeni.ui.screens.uploads.ReadingPracticeGeneratorScreen
import org.junit.Rule
import org.junit.Test

class ReadingPracticeGeneratorScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBackButton_isDisplayed() {
        composeTestRule.setContent {
            ReadingPracticeGeneratorScreen(
                onBackClick = {},
                onNextButtonClick = {},
                onLeaveButtonClick = {}
            )
        }

        // Kiểm tra xem nút quay lại có hiển thị không
        composeTestRule.onNodeWithContentDescription("BackIcon").assertIsDisplayed()
    }

    @Test
    fun testTitle_isDisplayed() {
        composeTestRule.setContent {
            ReadingPracticeGeneratorScreen(
                onBackClick = {},
                onNextButtonClick = {},
                onLeaveButtonClick = {}
            )
        }

        // Kiểm tra tiêu đề "Tạo đề đọc" có hiển thị không
        composeTestRule.onNodeWithText("Tạo đề đọc").assertIsDisplayed()
    }

    @Test
    fun testUploadButton_isDisplayed() {
        composeTestRule.setContent {
            ReadingPracticeGeneratorScreen(
                onBackClick = {},
                onNextButtonClick = {},
                onLeaveButtonClick = {}
            )
        }

        // Kiểm tra nút "TẢI TỆP" có hiển thị khi chế độ Upload được chọn
        composeTestRule.onNodeWithText("TẢI TỆP").assertIsDisplayed()
    }

    @Test
    fun testTextFieldInputChanges() {
        composeTestRule.setContent {
            ReadingPracticeGeneratorScreen(
                onBackClick = {},
                onNextButtonClick = {},
                onLeaveButtonClick = {}
            )
        }

        // Kiểm tra ô nhập liệu cho số câu hỏi
        composeTestRule.onNodeWithTag("numStatement").performTextInput("10")
        composeTestRule.onNodeWithTag("numStatement").assertExists()
    }

//    @Test
//    fun testNextButton_onClick() {
//        var isNextClicked = false
//
//        composeTestRule.setContent {
//            ReadingPracticeGeneratorScreen(
//                onBackClick = {},
//                onNextButtonClick = {
//                    isNextClicked = true
//                },
//                onLeaveButtonClick = {}
//            )
//        }
//
//        // Kiểm tra khi nhấn nút Next, isNextClicked sẽ là true
//        composeTestRule.onNodeWithText("Next").performClick()
//        assert(isNextClicked)
//    }

    @Test
    fun testPasteTextField_isDisplayed() {
        composeTestRule.setContent {
            PasteTextField(
                inputParagraph = "",
                inputNumStatement = "",
                onTextChanged = {},
                onNumStatementChanged = {}
            )
        }

        // Kiểm tra ô dán văn bản có hiển thị
        composeTestRule.onNodeWithText("Dán đoạn văn của bạn vào đây").assertIsDisplayed()
    }

    @Test
    fun testUploadFileDialog_isDisplayed() {
        composeTestRule.setContent {
            ReadingPracticeGeneratorScreen(
                onBackClick = {},
                onNextButtonClick = {},
                onLeaveButtonClick = {}
            )
        }
        composeTestRule.onNodeWithTag("upload file").performClick()
        // Kiểm tra xem Dialog tải tệp có hiển thị khi chế độ Upload được bật
        composeTestRule.onNodeWithText("TXT, up to 50MB").assertIsDisplayed()
    }
}
