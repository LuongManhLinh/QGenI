package com.example.qgeni.navigation

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.qgeni.data.preferences.ThemeMode
import com.example.qgeni.data.preferences.UserPreferenceManager
import com.example.qgeni.ui.screens.navigation.QGNavHost
import com.example.qgeni.ui.screens.navigation.Screen
import org.bson.types.ObjectId
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class QGNavHostNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupNavHost() {
        composeTestRule.setContent {
            val context = LocalContext.current
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
                val fakeUserId = ObjectId("507f1f77bcf86cd799439011")
                UserPreferenceManager.saveUserId(context, fakeUserId)

            }
            QGNavHost(
                navController = navController,
                currentTheme = ThemeMode.LIGHT,
                onThemeChange = {}
            )
        }
    }

    @Test
    fun qgNavHost_verifyStartDestination() {
        navController.assertCurrentRouteName(Screen.Welcome.route)
    }

    @Test
    fun qgNavHost_clickSignIn_navigatesToSignInScreen() {
        composeTestRule.onNodeWithTag("next_button")
            .performClick()
        composeTestRule.onNodeWithTag("Xác nhận").performClick()

        navController.assertCurrentRouteName(Screen.Home.route)

        composeTestRule.onNodeWithText("Tạo bài đọc").performClick()
        navController.assertCurrentRouteName(Screen.ReadingPracticeGenerator.route)

        composeTestRule.onNodeWithText("Dán đoạn văn của bạn vào đây").assertIsDisplayed()
        composeTestRule.onNodeWithText("Dán đoạn văn của bạn vào đây").performTextInput("test")

        composeTestRule.onNodeWithTag("input numQs").assertIsDisplayed()
        composeTestRule.onNodeWithTag("input numQs").performTextInput("1")

        composeTestRule.onNodeWithTag("next_button").assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription("BackIcon").performClick()
        navController.assertCurrentRouteName(Screen.Home.route)

        composeTestRule.onNodeWithText("Balo đề").performClick()
        navController.assertCurrentRouteName(Screen.Selection.route)

        composeTestRule.onNodeWithText("Đề bài đọc").performClick()
        navController.assertCurrentRouteName(Screen.ReadingPracticeList.route)



    }

}
