package com.example.qgeni.upload

import android.content.Context
import android.net.Uri
import com.example.qgeni.application.QgsApplication
import com.example.qgeni.data.model.ReadingPracticeItem
import com.example.qgeni.data.repository.DefaultReadingRepository
import com.example.qgeni.ui.screens.uploads.GeneratorState
import com.example.qgeni.ui.screens.uploads.ReadingPracticeGeneratorViewModel
import io.mockk.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.bson.types.ObjectId
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream
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
        mockkObject(DefaultReadingRepository)
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
        coEvery { QgsApplication.getReadingPracticeItem(any(), any()) } returns ObjectId("507f191e810c19729de860ea")
        viewModel.createReadingPractice()
        advanceUntilIdle()
        assertEquals(GeneratorState.Titling, viewModel.readingUIState.value.currentState)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun saveReadingPractice() = runTest {

        val testItemId = ObjectId("507f191e810c19729de860ea")
        viewModel.updateTitle("Reading Title")
        viewModel.updateFileContent("hello")
        viewModel.updateReadingInputParagraph("This is the paragraph content.")
        viewModel.updateReadingInputNumStatement("3")

        // Giả lập giá trị trả về của changeTitle
        coEvery { DefaultReadingRepository.changeTitle(testItemId, any()) } returns Unit

        viewModel.createReadingPractice()
        advanceUntilIdle()

        // Gán giá trị itemId trực tiếp để kiểm thử saveReadingPractice
        viewModel.javaClass.getDeclaredField("itemId").apply {
            isAccessible = true
            set(viewModel, testItemId)
        }
        val job = launch {
            viewModel.readingUIState.collect {
                state -> println("Intermediate State: $state")
            }
        }
        viewModel.saveReadingPractice()

        advanceUntilIdle()

        assertEquals(GeneratorState.Success, viewModel.readingUIState.value.currentState)

        job.cancel()
        Dispatchers.resetMain()
    }


}




