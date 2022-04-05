package com.luc.data.remote.firebase.auth

import com.google.firebase.auth.*
import kotlinx.coroutines.tasks.await


class AuthenticationDataSourceImpl(
    private val firebaseAuth: FirebaseAuth,
) : AuthenticationDataSource {

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): FirebaseUser? {
        return firebaseAuth
            .signInWithEmailAndPassword(email, password)
            .await().user
    }

    override suspend fun signUpWithEmailAndPassword(
        fullName: String,
        email: String,
        password: String
    ): FirebaseUser? {
        return firebaseAuth
            .createUserWithEmailAndPassword(email, password)
            .await().user
    }

    override suspend fun signInWithGoogle(token: String): FirebaseUser? {
        val credentials = GoogleAuthProvider.getCredential(token, null)
        return firebaseAuth
            .signInWithCredential(credentials)
            .await().user
    }

    override suspend fun signInAnonymous(): FirebaseUser? {
        return firebaseAuth.signInAnonymously().await().user
    }

    override fun getUserLogged() = firebaseAuth.currentUser

    override fun signOut() {
        firebaseAuth.signOut()
    }

    override suspend fun getToken() =
        firebaseAuth.currentUser?.getIdToken(true)?.await()?.token
}