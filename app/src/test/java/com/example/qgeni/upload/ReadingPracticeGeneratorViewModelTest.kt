package com.example.qgeni.upload

import ReadingPracticeGeneratorViewModel
import android.content.Context
import android.net.Uri
import com.example.qgeni.application.QgsApplication
import com.example.qgeni.data.model.ReadingPracticeItem
import com.example.qgeni.data.repository.DefaultReadingRepository
import com.example.qgeni.ui.screens.uploads.GeneratorState
import io.mockk.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

class ReadingPracticeGeneratorViewModelTest {
    private lateinit var viewModel: ReadingPracticeGeneratorViewModel
    private lateinit var mockContext: Context
    private lateinit var mockUri: Uri
    private var mockRepository = mockk<DefaultReadingRepository>(relaxed = true)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        mockContext = mockk(relaxed = true)
        mockUri = mockk()
        mockkStatic(Uri::class)
        mockkObject(QgsApplication)
        every { Uri.parse(any()) } returns mockUri
        viewModel = ReadingPracticeGeneratorViewModel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun creatingPractice() = runTest {
        viewModel.updateTitle("Reading Title")
        viewModel.updateFileContent("hello")
        viewModel.updateReadingInputParagraph("This is the paragraph content.")
        viewModel.updateReadingInputNumStatement("3")

        val practiceItem = mockk<ReadingPracticeItem>()
        coEvery { QgsApplication.getReadingPracticeItem(any(), any()) } returns practiceItem

        viewModel.createReadingPractice()
        advanceUntilIdle()

        assertEquals(GeneratorState.Titling, viewModel.readingUIState.value.currentState)
    }


}
