package com.luc.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.luc.common.NetworkStatus
import com.luc.common.model.UserProfile
import com.luc.domain.usecases.AddUserUseCase
import com.luc.domain.usecases.LoginUseCase

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    fun signUpWithEmailAndPassword(fullName: String, email: String, password: String) = liveData {
        emit(NetworkStatus.Loading)
        emit(loginUseCase.signUpWithEmailAndPassword(fullName, email, password))
    }

    fun signInWithEmailAndPassword(email: String, password: String) = liveData {
        emit(NetworkStatus.Loading)
        emit(loginUseCase.signInWithEmailAndPassword(email, password))
    }

    fun signInWithGoogle(token: String) =
        liveData {
            emit(NetworkStatus.Loading)
            emit(loginUseCase.signInWithGoogle(token))
        }

    fun getUserLogged() = liveData {
        emit(loginUseCase.getUserLogged())
    }

    fun signOut() = loginUseCase.signOut()
}