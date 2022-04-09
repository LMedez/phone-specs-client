package com.luc.data

import com.luc.common.NetworkStatus
import com.luc.common.model.phonespecs.PhoneDetail
import com.luc.data.remote.api.ApiErrorCodes
import com.luc.data.remote.api.ApiServiceDataSource
import com.luc.data.remote.api.ApiServiceException
import com.luc.data.remote.firebase.auth.AuthenticationDataSource
import com.luc.domain.ApiServiceRepository
import java.net.SocketTimeoutException

class ApiServiceRepositoryImpl(
    private val apiService: ApiServiceDataSource,
    private val authenticationDataSource: AuthenticationDataSource
) :
    ApiServiceRepository {
    override suspend fun getLatestPhones(limit: Int?): NetworkStatus<List<PhoneDetail>> {
        return try {
            val token = "Bearer ${authenticationDataSource.getToken()}"
            val data = apiService.getLatestPhones(limit, token)
            NetworkStatus.Success(data)

        } catch (e: Exception) {
            exceptionsHandler(e)
        }
    }

    override suspend fun getWithBestCamera(
        limit: Int?,
        brand: String?
    ): NetworkStatus<List<PhoneDetail>> {
        return try {
            val token = "Bearer ${authenticationDataSource.getToken()}"
            val data = apiService.getWithBestCamera(limit, brand, token)
            NetworkStatus.Success(data)

        } catch (e: Exception) {
            exceptionsHandler(e)
        }
    }

    override suspend fun search(limit: Int?, search: String?): NetworkStatus<List<PhoneDetail>> {
        return try {
            val token = "Bearer ${authenticationDataSource.getToken()}"
            val data = apiService.search(limit, search, token)
            NetworkStatus.Success(data)

        } catch (e: Exception) {
            exceptionsHandler(e)
        }
    }

    private fun exceptionsHandler(exception: Exception): NetworkStatus<List<PhoneDetail>> {
        return try {
            when (exception) {
                is ApiServiceException -> {
                    NetworkStatus.Error(
                        exception,
                        exception.errorDetail.errorCode
                    )
                }
                is SocketTimeoutException -> NetworkStatus.Error(exception, ApiErrorCodes.TIMEOUT)
                else -> NetworkStatus.Error(Exception(), "Unexpected error occurred")
            }
        } catch (e: Exception) {
            NetworkStatus.Error(e, "Unexpected error occurred")
        }
    }
}

