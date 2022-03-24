package com.luc.common.model

import android.net.Uri

data class UserProfile(
    val uId: String = "",
    val tokenId: String = "",
    val userName: String = "",
    val email: String = "",
    val photoUri: Uri?,
    val providerType: ProviderType = ProviderType.BASIC
)