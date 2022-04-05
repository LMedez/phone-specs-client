package com.luc.common.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.luc.common.model.ProviderType
import com.luc.common.model.UserProfile

@Entity
data class UserProfileEntity(
    @PrimaryKey val uId: String = "",
    val userName: String = "",
    val email: String? = "",
    val photoUri: String? = "",
    val providerType: String?
)

fun UserProfileEntity.asUserProfile(): UserProfile = UserProfile(
    this.uId,
    this.userName,
    this.email,
    this.photoUri,
    ProviderType.valueOf(this.providerType ?: "BASIC")
)
