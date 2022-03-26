package com.luc.data.remote.firebase.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.luc.common.NetworkStatus
import com.luc.common.model.UserProfile
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class FirestoreDataImpl(private val firestore: FirebaseFirestore) : FirestoreData {
    override suspend fun addUserProfile(userProfile: UserProfile): NetworkStatus<UserProfile> {
        return try {
            firestore.collection("users").add(userProfile).await()
            NetworkStatus.Success(userProfile)
        } catch (e: FirebaseFirestoreException) {
            NetworkStatus.Error(e, e.message ?: "Error on add new user")
        }
    }

    override suspend fun getUserProfile(uid: String): UserProfile? {
        return try {
            val data = firestore.collection("users").whereEqualTo("uid", uid).get().await()
                .toObjects(UserProfile::class.java)
            return if (data.isEmpty()) null
            else data.first()
        } catch (e: FirebaseFirestoreException) {
            null
        }
    }

}