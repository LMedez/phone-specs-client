package com.luc.common.model

import android.net.Uri
import android.os.Parcelable
import com.luc.common.entities.UserProfileEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserProfile(
    val uId: String = "",
    val userName: String = "",
    val email: String? = "",
    val photoUri: String? = "",
    val providerType: ProviderType = ProviderType.BASIC
) : Parcelable

fun UserProfile.asUserProfileEntity() =
    UserProfileEntity(this.uId, this.userName, this.email, this.photoUri, this.providerType.name)