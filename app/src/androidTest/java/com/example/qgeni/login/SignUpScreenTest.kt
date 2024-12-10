package com.example.qgeni.login

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.qgeni.data.repository.DefaultAccountRepository
import com.example.qgeni.ui.screens.login.SignUpScreen
import com.example.qgeni.ui.screens.login.SignUpViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignUpScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    @Test
    fun testSignUpScreen_elementsAreDisplayed() {
        // Thiết lập giao diện cần kiểm thử
        composeTestRule.setContent {
            SignUpScreen(
                onBackClick = {},
                onSignUpSuccess = {},
                onSignInClick = {}
            )
        }

        // Kiểm tra xem các thành phần UI có hiển thị đúng không
        composeTestRule.onNodeWithText("Đăng ký").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tên tài khoản").assertIsDisplayed()
        composeTestRule.onNodeWithText("Số điện thoại").assertIsDisplayed()
        composeTestRule.onNodeWithText("Địa chỉ email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Mật khẩu").assertIsDisplayed()
        composeTestRule.onNodeWithText("Đồng ý với").assertIsDisplayed()
        composeTestRule.onNodeWithText(" điều khoản của chúng tôi").assertIsDisplayed()
        composeTestRule.onNodeWithText("Đã có tài khoản?").assertIsDisplayed()
    }

    @Test
    fun testUserCanFillForm() {
        composeTestRule.setContent {
            SignUpScreen(
                onBackClick = {},
                onSignUpSuccess = {},
                onSignInClick = {}
            )
        }

        // Nhập dữ liệu
        composeTestRule.onNodeWithText("Tên tài khoản").performTextInput("test_user")
        composeTestRule.onNodeWithText("Số điện thoại").performTextInput("123456789")
        composeTestRule.onNodeWithText("Địa chỉ email").performTextInput("test@mail.com")
        composeTestRule.onNodeWithTag("password_field").performTextInput("password123")

        // Kiểm tra dữ liệu đã nhập vào
        composeTestRule.onNodeWithText("test_user").assertExists()
        composeTestRule.onNodeWithText("123456789").assertExists()
        composeTestRule.onNodeWithText("test@mail.com").assertExists()
        composeTestRule.onNodeWithTag("password_field").assertExists()
    }

    @Test
    fun testTermsCheckbox() {
        composeTestRule.setContent {
            SignUpScreen(
                onBackClick = {},
                onSignUpSuccess = {},
                onSignInClick = {},
            )
        }
        composeTestRule.onNodeWithTag("terms_checkbox")
            .performClick()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testSignUpSuccessDialog() = runTest {
        // Đặt nội dung cho ComposeTestRule
        composeTestRule.setContent {
            SignUpScreen(
                onBackClick = {},
                onSignUpSuccess = {},
                onSignInClick = {},
            )
        }

        // Kiểm tra trạng thái ban đầu của showSuccessDialog
        composeTestRule.onNodeWithTag("next_button").assertExists()

        // Giả lập người dùng nhấn nút "NextButton"
        composeTestRule.onNodeWithTag("next_button").performClick()

        advanceUntilIdle()

        // Kiểm tra xem dialog "TẠO TÀI KHOẢN THÀNH CÔNG" có hiển thị không
        composeTestRule.onNodeWithText("TẠO TÀI KHOẢN THÀNH CÔNG").assertIsDisplayed()

        // Nhấn nút "XÁC NHẬN" trong dialog
        composeTestRule.onNodeWithText("XÁC NHẬN").performClick()

        // Kiểm tra việc đóng dialog sau khi nhấn "XÁC NHẬN"
        composeTestRule.onNodeWithText("TẠO TÀI KHOẢN THÀNH CÔNG").assertDoesNotExist()
    }


}
