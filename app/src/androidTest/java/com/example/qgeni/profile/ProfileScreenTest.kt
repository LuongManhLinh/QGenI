package com.example.qgeni.profile

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.tooling.preview.Preview
import com.example.qgeni.data.preferences.ThemeMode
import com.example.qgeni.ui.screens.profile.LogOutConfirm
import com.example.qgeni.ui.screens.profile.ProfileScreen
import com.example.qgeni.ui.theme.QGenITheme
import org.junit.Rule
import org.junit.Test

class ProfileScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testProfileScreenDisplaysCorrectly() {
        // Thiết lập giao diện với dữ liệu giả
        composeTestRule.setContent {
            QGenITheme(dynamicColor = false) {
                ProfileScreen(
                    onBackClick = {},
                    onChangeInfoClick = {},
                    onLogOutClick = {},
                    currentTheme = ThemeMode.LIGHT,
                    onThemeChange = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Thay đổi thông tin cá nhân").assertIsDisplayed()
        composeTestRule.onNodeWithText("Chủ đề").assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription("BackIcon").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("next_button").assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription("BackIcon").performClick()

        composeTestRule.onNodeWithText("Thay đổi thông tin cá nhân").performClick()
        composeTestRule.onNodeWithContentDescription("next_button").performClick()
    }

    @Test
    fun testProfileScreenWithDifferentTheme() {
        composeTestRule.setContent {
            QGenITheme(dynamicColor = false) {
                ProfileScreen(
                    onBackClick = {},
                    onChangeInfoClick = {},
                    onLogOutClick = {},
                    currentTheme = ThemeMode.DARK,
                    onThemeChange = {}
                )
            }
        }

        // Kiểm tra chủ đề hiển thị chính xác
        composeTestRule.onNodeWithText("Tối").assertIsDisplayed()
    }

    @Test
    fun testLogOutConfirmDialog() {
        composeTestRule.setContent {
            QGenITheme(dynamicColor = false) {
                LogOutConfirm(
                    onNextButtonClick = {},
                    onDismissRequest = {}
                )
            }
        }

        // Kiểm tra sự xuất hiện của dialog đăng xuất
        composeTestRule.onNodeWithText("Bạn chắc chắn muốn đăng xuất").assertIsDisplayed()
        composeTestRule.onNodeWithText("HỦY").assertIsDisplayed()
        composeTestRule.onNodeWithText("XÁC NHẬN").assertIsDisplayed()

        // Kiểm tra click vào nút "HỦY"
        composeTestRule.onNodeWithText("HỦY").performClick()

        // Kiểm tra click vào nút "XÁC NHẬN"
        composeTestRule.onNodeWithText("XÁC NHẬN").performClick()
    }
}
