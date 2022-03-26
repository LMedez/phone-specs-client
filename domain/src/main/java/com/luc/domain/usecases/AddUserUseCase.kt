package com.luc.domain.usecases

import com.luc.common.model.UserProfile
import com.luc.domain.FirestoreRepository

class AddUserUseCase(private val firestoreRepository: FirestoreRepository) {

    suspend fun addUserProfile(userProfile: UserProfile) = firestoreRepository.addUser(userProfile)

}