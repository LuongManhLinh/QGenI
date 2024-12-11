package com.example.qgeni.profile

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performClick
import com.example.qgeni.ui.screens.profile.ChangeInformationScreen
import com.example.qgeni.ui.theme.QGenITheme
import org.junit.Rule
import org.junit.Test

class ChangeInformationScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun changeInformationScreenDisplaysCorrectlyAndHandlesInput() {
        composeTestRule.setContent {
            QGenITheme(dynamicColor = false) {
                ChangeInformationScreen(
                    onBackClick = {},
                    onNextButtonClick = {}
                )
            }
        }

        // Kiểm tra tiêu đề "Thay đổi thông tin" được hiển thị
        composeTestRule.onNodeWithText("Thay đổi thông tin").assertIsDisplayed()

        // Kiểm tra các trường thông tin được hiển thị
        composeTestRule.onNodeWithText("Tên tài khoản").assertIsDisplayed()
        composeTestRule.onNodeWithText("Số điện thoại").assertIsDisplayed()
        composeTestRule.onNodeWithText("Địa chỉ email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Giới tính").assertIsDisplayed()

        // Nhập thông tin vào các trường
        composeTestRule.onNodeWithText("Tên tài khoản").performTextInput("John Doe")
        composeTestRule.onNodeWithText("Số điện thoại").performTextInput("1234567890")
        composeTestRule.onNodeWithText("Địa chỉ email").performTextInput("john.doe@example.com")
        composeTestRule.onNodeWithText("Giới tính").performTextInput("Nam")

        // Nhấn nút "Tiếp theo"
        composeTestRule.onNodeWithTag("next_button").performClick()
    }
}
