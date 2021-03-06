package com.luc.domain

import com.luc.common.NetworkStatus
import com.luc.common.model.UserProfile

interface LoginRepository {
    suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): NetworkStatus<UserProfile>

    suspend fun signUpWithEmailAndPassword(
        fullName: String,
        email: String,
        password: String
    ): NetworkStatus<UserProfile>

    suspend fun signInWithGoogle(token: String): NetworkStatus<UserProfile>

    suspend fun signInAnonymous(): NetworkStatus<UserProfile>

    suspend fun getUserLogged(): UserProfile?

    fun signOut()


}