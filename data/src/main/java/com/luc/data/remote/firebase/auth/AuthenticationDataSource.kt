package com.luc.data.remote.firebase.auth

import com.google.firebase.auth.FirebaseUser
import com.luc.common.NetworkStatus
import com.luc.common.model.UserProfile

interface AuthenticationDataSource {
    suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): FirebaseUser?

    suspend fun signUpWithEmailAndPassword(
        fullName: String,
        email: String,
        password: String
    ): FirebaseUser?

    suspend fun signInWithGoogle(token: String): FirebaseUser?

    suspend fun signInAnonymous(): FirebaseUser?

    fun getUserLogged(): FirebaseUser?

    suspend fun getToken(): String?

    fun signOut()
}