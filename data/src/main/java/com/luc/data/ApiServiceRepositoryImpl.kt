package com.luc.data

import com.luc.common.NetworkStatus
import com.luc.common.model.phonespecs.PhoneDetail
import com.luc.data.remote.api.ApiService
import com.luc.data.remote.api.ApiServiceDataSource
import com.luc.domain.ApiServiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class ApiServiceRepositoryImpl(private val apiService: ApiServiceDataSource) :
    ApiServiceRepository {
    override suspend fun getLatestPhones(limit: Int?): NetworkStatus<List<PhoneDetail>> {
        return apiService.getLatestPhones(limit)
    }

    override suspend fun getWithBestCamera(
        limit: Int?,
        brand: String?
    ): NetworkStatus<List<PhoneDetail>> {
        return apiService.getWithBestCamera(limit, brand)
    }
}

