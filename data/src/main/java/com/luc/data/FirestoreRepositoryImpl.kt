package com.luc.data

import com.luc.common.NetworkStatus
import com.luc.common.model.User
import com.luc.common.model.UserProfile
import com.luc.data.local.LocalDataSource
import com.luc.data.remote.firebase.firestore.FirestoreData
import com.luc.domain.FirestoreRepository

class FirestoreRepositoryImpl(
    private val firestoreData: FirestoreData,
    private val localDataSource: LocalDataSource
) : FirestoreRepository {
    override suspend fun addUser(userProfile: UserProfile): NetworkStatus<UserProfile> {
        return firestoreData.addUserProfile(userProfile)
    }

}