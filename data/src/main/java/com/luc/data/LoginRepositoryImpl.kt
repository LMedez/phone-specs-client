package com.luc.data

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestoreException
import com.luc.common.NetworkStatus
import com.luc.common.model.ProviderType
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
        return try {
            val firebaseUser = authenticationDataSource.signInWithEmailAndPassword(email, password)
            val user = localDataSource.getUser(firebaseUser!!.uid)
            if (user == null) {
                val userFromFirestore = firestoreData.getUserProfile(firebaseUser.uid)
                localDataSource.insertUser(userFromFirestore!!)
                return NetworkStatus.Success(userFromFirestore)
            } else NetworkStatus.Success(user)
        } catch (e: Exception) {
            exceptionsHandler(e)
        }
    }

    override suspend fun signUpWithEmailAndPassword(
        fullName: String,
        email: String,
        password: String
    ): NetworkStatus<UserProfile> {
        return try {
            val user =
                authenticationDataSource.signUpWithEmailAndPassword(fullName, email, password)
            localDataSource.insertUser(user!!.asUserProfile(fullName))
            firestoreData.addUserProfile(user.asUserProfile(fullName))
            NetworkStatus.Success(user.asUserProfile())
        } catch (e: Exception) {
            exceptionsHandler(e)
        }
    }

    override suspend fun signInWithGoogle(token: String): NetworkStatus<UserProfile> {
        return try {
            val firebaseUser = authenticationDataSource.signInWithGoogle(token)
            val user = localDataSource.getUser(firebaseUser!!.uid)
            if (user == null) {
                val userFromFirestore = firestoreData.getUserProfile(firebaseUser.uid)
                if (userFromFirestore == null) {
                    firestoreData.addUserProfile(firebaseUser.asUserProfile())
                    localDataSource.insertUser(firebaseUser.asUserProfile())
                    return NetworkStatus.Success(firebaseUser.asUserProfile())
                } else localDataSource.insertUser(userFromFirestore)
                return NetworkStatus.Success(userFromFirestore)
            }
            return NetworkStatus.Success(localDataSource.getUser(firebaseUser.uid)!!)
        } catch (e: Exception) {
            exceptionsHandler(e)
        }
    }

    override suspend fun signInAnonymous(): NetworkStatus<UserProfile> {
        return try {
            NetworkStatus.Success(authenticationDataSource.signInAnonymous()!!.asUserProfile())
        }catch (e: Exception) {
            exceptionsHandler(e)
        }
    }

    override suspend fun getUserLogged(): UserProfile? {
        return authenticationDataSource.getUserLogged()?.asUserProfile()
    }

    override fun signOut() {
        authenticationDataSource.signOut()
    }

    private fun exceptionsHandler(exception: Exception): NetworkStatus<UserProfile> =
        when (exception) {
            is FirebaseAuthException -> NetworkStatus.Error(exception, exception.errorCode)
            is FirebaseFirestoreException -> NetworkStatus.Error(
                exception,
                "Unexpected error occurred"
            )
            else -> NetworkStatus.Error(exception, "Unexpected error occurred")
        }
}


fun FirebaseUser.asUserProfile(displayName: String = ""): UserProfile {
    var provider: ProviderType = ProviderType.BASIC
    providerData.forEach {
        provider = when (it.providerId) {
            GoogleAuthProvider.PROVIDER_ID -> ProviderType.GOOGLE
            EmailAuthProvider.PROVIDER_ID -> ProviderType.BASIC
            else -> ProviderType.ANONYMOUS
        }
    }
    return UserProfile(
        this.uid,
        this.displayName ?: displayName,
        this.email,
        this.photoUrl.toString(),
        provider
    )

}
