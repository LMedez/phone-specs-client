package com.luc.domain.usecases

import com.luc.common.NetworkStatus
import com.luc.common.model.UserProfile
import com.luc.domain.LoginRepository
import kotlin.math.log

class LoginUseCase(private val loginRepository: LoginRepository) {
    suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): NetworkStatus<UserProfile> = loginRepository.signInWithEmailAndPassword(email, password)

    suspend fun signUpWithEmailAndPassword(
        fullName: String,
        email: String,
        password: String
    ): NetworkStatus<UserProfile> =
        loginRepository.signUpWithEmailAndPassword(fullName, email, password)

    suspend fun signInWithGoogle(token: String): NetworkStatus<UserProfile> =
        loginRepository.signInWithGoogle(token)

    suspend fun signInAnonymous() = loginRepository.signInAnonymous()

    suspend fun getUserLogged() = loginRepository.getUserLogged()

    fun signOut() = loginRepository.signOut()
}