package com.luc.data.remote.api

import com.luc.common.model.bodyToErrorApi
import com.luc.common.model.phonespecs.PhoneDetail

class ApiServiceDataSource(
    private val apiService: ApiService,
) {
    suspend fun getLatestPhones(limit: Int? = null, token: String): List<PhoneDetail> {
        val data = apiService.getLatestPhones(token, limit)
        if (!data.isSuccessful) {
            throw ApiServiceException(bodyToErrorApi(data.errorBody()?.string() ?: ""))
        }
        return data.body() ?: emptyList()
    }

    suspend fun getWithBestCamera(
        limit: Int? = null,
        brand: String? = null,
        token: String
    ): List<PhoneDetail> {

        val data = apiService.getWithBestCamera(token, limit, brand)

        if (!data.isSuccessful) {
            throw ApiServiceException(bodyToErrorApi(data.errorBody()?.string() ?: ""))
        }
        return data.body() ?: emptyList()
    }

    suspend fun search(
        limit: Int? = null,
        search: String? = null,
        token:String
    ): List<PhoneDetail> {
        val data = apiService.search(token, limit, search)
        if (!data.isSuccessful) {
            throw ApiServiceException(bodyToErrorApi(data.errorBody()?.string() ?: ""))
        }
        return data.body() ?: emptyList()
    }
}

