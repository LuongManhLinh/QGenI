package com.example.qgeni.login

import android.content.Context
import com.example.qgeni.data.preferences.UserPreferenceManager
import com.example.qgeni.data.repository.DefaultAccountRepository
import com.example.qgeni.ui.screens.login.SignInEvent
import com.example.qgeni.ui.screens.login.SignInUIState
import com.example.qgeni.ui.screens.login.SignInViewModel
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.bson.types.ObjectId
import org.junit.After
import org.junit.Before
import org.junit.Test

class SignInViewModelTest {
    private lateinit var viewModel: SignInViewModel
    private lateinit var mockContext: Context

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        viewModel = SignInViewModel()
        mockContext = mockk()
        mockkObject(DefaultAccountRepository)
        mockkObject(UserPreferenceManager)

        Dispatchers.setMain(StandardTestDispatcher())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun signInSuccess() = runTest {
        val testDispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)
        coEvery {
            DefaultAccountRepository.checkExistence(
                usernameOrEmailOrPhone = "test@example.com",
                password = "password"
            )
        } returns ObjectId("507f191e810c19729de860ea")

        coEvery {
            UserPreferenceManager.saveUserId(
                mockContext,
                ObjectId("507f191e810c19729de860ea")
            )
        } returns Unit

        val uiState = mutableListOf<SignInUIState>()
        val job = launch {
            viewModel.uiState.collect {
                uiState.add(it)
                println(uiState)
            }
        }

        viewModel.updateEmail("test@example.com")
        viewModel.updatePassword("password")
        viewModel.signIn(mockContext)

        advanceUntilIdle()
        runCurrent()

        assertEquals(SignInEvent.SUCCESS, uiState.last().signInEvent)
        job.cancel()
        Dispatchers.resetMain()
    }

    @After
    fun teardown() {
        unmockkObject(DefaultAccountRepository)
        unmockkObject(UserPreferenceManager)
    }
}