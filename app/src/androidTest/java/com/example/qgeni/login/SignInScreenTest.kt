package com.example.qgeni.login

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.qgeni.ui.screens.login.SignInScreen
import org.junit.Rule
import org.junit.Test

class SignInScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testSignInScreen_elementsAreDisplayed() {
        // Set nội dung để kiểm thử
        composeTestRule.setContent {
            SignInScreen(
                onBackClick = {},
                onSignInSuccess = {},
                onSignUpClick = {},
                onForgotPasswordClick = {}
            )
        }

        // Kiểm tra các thành phần chính của giao diện có hiển thị không
        composeTestRule.onNodeWithText("Đăng nhập").assertIsDisplayed()
        composeTestRule.onNodeWithText("Chào mừng bạn quay trở lại").assertIsDisplayed()
        composeTestRule.onNodeWithText("Địa chỉ email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Mật khẩu").assertIsDisplayed()
        composeTestRule.onNodeWithText("Quên mật khẩu?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Chưa có tài khoản?").assertIsDisplayed()
        composeTestRule.onNodeWithText(" Đăng ký").assertIsDisplayed()
    }

    @Test
    fun testUserCanInputEmailAndPassword() {
        composeTestRule.setContent {
            SignInScreen(
                onBackClick = {},
                onSignInSuccess = {},
                onSignUpClick = {},
                onForgotPasswordClick = {}
            )
        }

        // Nhập dữ liệu
        composeTestRule.onNodeWithText("Địa chỉ email").performTextInput("test@example.com")
        composeTestRule.onNodeWithText("Mật khẩu").performTextInput("passwordtest")

        composeTestRule.onNodeWithText("test@example.com").assertExists()

        val toggleIcon = composeTestRule.onNode(hasContentDescription("passwordVisible"))
        toggleIcon.performClick()
        composeTestRule.onNodeWithText("passwordtest").assertExists()

    }

    @Test
    fun testTogglePasswordVisibility() {
        composeTestRule.setContent {
            SignInScreen(
                onBackClick = {},
                onSignInSuccess = {},
                onSignUpClick = {},
                onForgotPasswordClick = {}
            )
        }
        val toggleIcon = composeTestRule.onNode(hasContentDescription("passwordVisible"))
        toggleIcon.performClick()
        toggleIcon.assertExists()
    }

//    @Test
//    fun testSignInButtonClick() {
//        var signInSuccessTriggered = false
//        composeTestRule.setContent {
//            SignInScreen(
//                onBackClick = {},
//                onSignInSuccess = { signInSuccessTriggered = true },
//                onSignUpClick = {},
//                onForgotPasswordClick = {}
//            )
//        }
//
//        // Nhấn nút "NextButton"
//        composeTestRule.onNodeWithTag("next_button").performClick()
//        // Kiểm tra hành động sau khi nhấn
//        composeTestRule.waitForIdle()
//        Log.i("signin", signInSuccessTriggered.toString())
//        assert(signInSuccessTriggered)
//    }

    @Test
    fun testSignUpClick() {
        var signUpClicked = false
        composeTestRule.setContent {
            SignInScreen(
                onBackClick = {},
                onSignInSuccess = {},
                onSignUpClick = { signUpClicked = true },
                onForgotPasswordClick = {}
            )
        }

        // Nhấn nút "Đăng ký"
        composeTestRule.onNodeWithText(" Đăng ký").performClick()

        // Kiểm tra trạng thái
        assert(signUpClicked)
    }

    @Test
    fun testForgotPasswordClick() {
        var forgotPasswordClicked = false
        composeTestRule.setContent {
            SignInScreen(
                onBackClick = {},
                onSignInSuccess = {},
                onSignUpClick = {},
                onForgotPasswordClick = { forgotPasswordClicked = true }
            )
        }

        // Nhấn vào "Quên mật khẩu?"
        composeTestRule.onNodeWithText("Quên mật khẩu?").performClick()

        // Kiểm tra trạng thái
        assert(forgotPasswordClicked)
    }
}
