package com.luc.domain

import com.luc.common.NetworkStatus
import com.luc.common.model.UserProfile

interface FirestoreRepository {
    suspend fun addUser(userProfile: UserProfile): NetworkStatus<UserProfile>
}