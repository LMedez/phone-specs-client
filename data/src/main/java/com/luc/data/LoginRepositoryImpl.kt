package com.luc.data

import com.luc.common.NetworkStatus
import com.luc.common.model.User
import com.luc.common.model.UserProfile
import com.luc.data.local.LocalDataSource
import com.luc.data.remote.firebase.auth.AuthenticationDataSource
import com.luc.data.remote.firebase.firestore.FirestoreData
import com.luc.domain.LoginRepository

class LoginRepositoryImpl(
    private val firestoreData: FirestoreData,
    private val authenticationDataSource: AuthenticationDataSource,
    private val localDataSource: LocalDataSource
) : LoginRepository {

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): NetworkStatus<UserProfile> {
        return authenticationDataSource.signInWithEmailAndPassword(email, password)
    }

    override suspend fun signUpWithEmailAndPassword(
        fullName: String,
        email: String,
        password: String
    ): NetworkStatus<UserProfile> {
        return authenticationDataSource.signUpWithEmailAndPassword(email, password, fullName)
    }

    override suspend fun signInWithGoogle(token: String): NetworkStatus<UserProfile> {
        return authenticationDataSource.signInWithGoogle(token)
    }

    override suspend fun getUserLogged(): UserProfile? {
        return authenticationDataSource.getUserLogged()
    }

    override fun signOut() {
        authenticationDataSource.signOut()
    }

}