package com.luc.data.remote.firebase.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.luc.common.NetworkStatus
import com.luc.common.model.ProviderType
import com.luc.common.model.UserProfile
import com.luc.data.remote.firebase.firestore.FirestoreData
import kotlinx.coroutines.tasks.await


class AuthenticationDataSourceImpl(
    private val firebaseAuth: FirebaseAuth,
    private val firestoreData: FirestoreData
) :
    AuthenticationDataSource {

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): NetworkStatus<UserProfile> {
        return try {
            val data = firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .await()
            if (data.user == null) return NetworkStatus.Error(
                NullPointerException(),
                "The user is null"
            )
            NetworkStatus.Success(data.user!!.asUserProfile())
        } catch (e: FirebaseAuthException) {
            NetworkStatus.Error(e, e.message ?: "An Error occurred")
        }
    }

    override suspend fun signUpWithEmailAndPassword(
        fullName: String,
        email: String,
        password: String
    ): NetworkStatus<UserProfile> {
        return try {
            val data = firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .await()
            if (data.user == null) return NetworkStatus.Error(
                NullPointerException(),
                "The user is null"
            )
            firestoreData.addUserProfile(data.user!!.asUserProfile(fullName))
            NetworkStatus.Success(data.user!!.asUserProfile())
        } catch (e: FirebaseAuthException) {
            NetworkStatus.Error(e, e.message ?: "An Error occurred")
        }
    }

    override suspend fun signInWithGoogle(token: String): NetworkStatus<UserProfile> {
        return try {
            val credentials = GoogleAuthProvider.getCredential(token, null)
            val data = firebaseAuth
                .signInWithCredential(credentials)
                .await()

            if (data.user != null) {
                if (data.additionalUserInfo?.isNewUser == true) {
                    firestoreData.addUserProfile(data.user!!.asUserProfile())
                }
            } else return NetworkStatus.Error(NullPointerException(), "Null user")

            NetworkStatus.Success(data.user!!.asUserProfile())
        } catch (e: Exception) {
            NetworkStatus.Error(e, e.message ?: "")
        }
    }

    override suspend fun getUserLogged(): UserProfile? {
        return if (firebaseAuth.currentUser == null) {
            null
        } else {
            firestoreData.getUserProfile(firebaseAuth.currentUser!!.uid)
                ?: firebaseAuth.currentUser!!.asUserProfile()
        }
    }

    override fun signOut() {
        firebaseAuth.signOut()

    }

    suspend fun getToken() =
        firebaseAuth.currentUser?.getIdToken(true)?.await()?.token
}

fun FirebaseUser.asUserProfile(displayName: String = ""): UserProfile {
    var provider: ProviderType = ProviderType.BASIC
    var userName = ""
    providerData.forEach {
        if (it.providerId == "google.com") provider = ProviderType.GOOGLE
        userName = it.displayName ?: displayName
    }
    return UserProfile(
        this.uid, userName, this.email ?: "No email", this.photoUrl.toString(), provider
    )

}
