package com.example.qgeni.login


import android.util.Log
import com.example.qgeni.data.repository.DefaultAccountRepository
import com.example.qgeni.ui.screens.login.SignUpUIState
import com.example.qgeni.ui.screens.login.SignUpViewModel
import io.mockk.coEvery
import io.mockk.mockkObject
import io.mockk.unmockkObject
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


class SignUpViewModelTest {
    private lateinit var viewModel: SignUpViewModel

    @Before
    fun setup() {
        viewModel = SignUpViewModel()
        mockkObject(DefaultAccountRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun signUpTest() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        coEvery {
            DefaultAccountRepository.createAccount(
                username = "testuser",
                password = "password",
                email = "test@example.com",
                phoneNumber = null
            )
        } returns Unit

        val uiState = mutableListOf<SignUpUIState>()
        val job = launch {
            viewModel.uiState.collect {
                println(uiState)
                uiState.add(it)
            }
        }

        viewModel.updateUsername("testuser")
        viewModel.updatePassword("password")
        viewModel.updateEmail("test@example.com")
        viewModel.signUp()

        advanceUntilIdle()
        assertEquals(true, uiState.last().showSuccessDialog)

        job.cancel()
        Dispatchers.resetMain()
    }


    @After
    fun teardown() {
        unmockkObject(DefaultAccountRepository)
    }
}