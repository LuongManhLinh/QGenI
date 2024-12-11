package com.example.qgeni.practices

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.qgeni.data.model.ReadingAnswer
import com.example.qgeni.data.model.ReadingPracticeItem
import com.example.qgeni.data.model.ReadingQuestion
import com.example.qgeni.ui.screens.practices.ReadingPracticeScreen
import com.example.qgeni.ui.screens.practices.ReadingPracticeUIState
import com.example.qgeni.ui.screens.practices.ReadingPracticeViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.bson.types.ObjectId
import org.junit.Rule
import org.junit.Test
import java.util.Date

class ReadingPracticeTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun readingPracticeScreenDisplaysCorrectData() {
        val mockUIState = ReadingPracticeUIState(
            readingPracticeItem = ReadingPracticeItem(
                id = ObjectId("647f1b4f3c4e88a6a95b9e48"),
                title = "Reading Practice Test",
                passage = "This is a sample passage for the test.",
                creationDate = Date(),
                isNew = true,
                questionList = listOf(
                    ReadingQuestion(statement = "Is this a sample question?", answer = ReadingAnswer.TRUE),
                    ReadingQuestion(statement = "Is this another question?", answer = ReadingAnswer.FALSE)
                )
            ),
            isHighlightEnabled = true,
            isHighlightMode = false,
            time = 120000L,
            highlightedIndices = listOf(5, 10),
            currentQuestionIndex = 0,
            selectAnswer = null,
            answeredQuestions = mutableMapOf(0 to "TRUE"),
            isComplete = false
        )



        val mockViewModel = object : ReadingPracticeViewModel("647f1b4f3c4e88a6a95b9e48") {
            override val uiState = MutableStateFlow(mockUIState)
            override fun checkScore(): Int = 1
        }

        composeTestRule.setContent {
            ReadingPracticeScreen(
                idHexString = "647f1b4f3c4e88a6a95b9e48",
                onNextButtonClick = {},
                onBackClick = {},
                readingPracticeViewModel = mockViewModel
            )
        }
        composeTestRule.onNodeWithContentDescription("BackIcon").assertIsDisplayed()
        composeTestRule.onNodeWithText("Reading").assertIsDisplayed()
        composeTestRule.onNodeWithText("Thời gian làm bài: ").assertIsDisplayed()
        composeTestRule.onNodeWithText("HighLight").assertIsDisplayed()
        composeTestRule.onNodeWithText("This is a sample passage for the test.").assertIsDisplayed()
        composeTestRule.onNodeWithText("Question 1").assertIsDisplayed()

        composeTestRule.onNodeWithText("Eraser").assertIsDisplayed()
        composeTestRule.onNodeWithTag("highlight mode").performClick()

        composeTestRule.onNodeWithText("Highlight").assertIsDisplayed()
        composeTestRule.onNodeWithTag("highlight").performClick()


    }
}