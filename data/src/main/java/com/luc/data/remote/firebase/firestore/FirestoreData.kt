package com.luc.data.remote.firebase.firestore

import com.luc.common.model.UserProfile

interface FirestoreData {
    suspend fun addUserProfile(userProfile: UserProfile)
    suspend fun getUserProfile(uid: String) : UserProfile?
}