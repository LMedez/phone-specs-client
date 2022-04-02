package com.luc.domain

import com.luc.common.NetworkStatus
import com.luc.common.model.phonespecs.PhoneDetail

interface ApiServiceRepository {
    suspend fun getLatestPhones(limit: Int? = null): NetworkStatus<List<PhoneDetail>>

    suspend fun getWithBestCamera(
        limit: Int? = null,
        brand: String? = null
    ): NetworkStatus<List<PhoneDetail>>

}