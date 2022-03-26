package com.luc.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.luc.common.NetworkStatus
import com.luc.domain.usecases.LoginUseCase

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    var googleSignInClient: GoogleSignInClient? = null

    fun signUpWithEmailAndPassword(fullName: String, email: String, password: String) = liveData {
        emit(NetworkStatus.Loading)
        emit(loginUseCase.signUpWithEmailAndPassword(fullName, email, password))
    }

    fun signInWithEmailAndPassword(email: String, password: String) = liveData {
        emit(NetworkStatus.Loading)
        emit(loginUseCase.signInWithEmailAndPassword(email, password))
    }

    fun signInAnonymous(userName: String) = liveData {
        emit(NetworkStatus.Loading)
        emit(loginUseCase.signInAnonymous(userName))

    }

    fun signInWithGoogle(token: String) =
        liveData {
            emit(NetworkStatus.Loading)
            emit(loginUseCase.signInWithGoogle(token))
        }

    fun getUserLogged() = liveData {
        emit(loginUseCase.getUserLogged())
    }

    fun signOut(){
        googleSignInClient?.signOut()
        loginUseCase.signOut()
    }
}