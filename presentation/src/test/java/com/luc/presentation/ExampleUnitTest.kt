package com.luc.presentation

import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.luc.domain.LoginRepository
import com.luc.domain.usecases.LoginUseCase
import com.luc.presentation.viewmodel.LoginViewModel
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(JUnit4::class)
public class ExampleUnitTest {

//    @get:Rule
//    val instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()
//
//    @Mock
//    lateinit var loginUseCase: LoginUseCase
//
//    @Mock
//    lateinit var loginRepository: LoginRepository
//
//    lateinit var loginViewModel: LoginViewModel
//
//    @Before
//    fun setup() {
//        MockitoAnnotations.openMocks(this)
//        loginViewModel = LoginViewModel(loginUseCase)
//
//    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}