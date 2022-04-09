package com.luc.data.remote.api

import android.util.Log
import com.google.firebase.auth.FirebaseAuthException
import com.luc.common.NetworkStatus
import com.luc.common.model.bodyToErrorApi
import com.luc.common.model.phonespecs.PhoneDetail
import com.luc.data.remote.api.ApiErrorCodes.TIMEOUT
import com.luc.data.remote.firebase.auth.AuthenticationDataSource
import okhttp3.ResponseBody
import java.util.concurrent.TimeoutException

class ApiServiceDataSource(
    private val apiService: ApiService,
    private val authenticationDataSource: AuthenticationDataSource
) {
    suspend fun getLatestPhones(limit: Int? = null): NetworkStatus<List<PhoneDetail>> {
        return try {
            val token = authenticationDataSource.getToken()
            if (token.isNullOrEmpty()) return NetworkStatus.Error(
                FirebaseAuthException(
                    "404",
                    "Unauthorized: miss token or token is null"
                ), "token error"
            )
            val bearerToken = "Bearer $token"
            val handling = apiService.getLatestPhones(bearerToken, limit)

            if (!handling.isSuccessful) {
                val errorApi = bodyToErrorApi(handling.errorBody()?.string() ?: "")
            }
            val data = apiService.getLatestPhones(bearerToken, limit).body()
            if (data.isNullOrEmpty()) return NetworkStatus.Error(
                null,
                "Error fetching data from server"
            )
            NetworkStatus.Success(data)
        } catch (e: Exception) {
            Log.d("tests", e.message?:"null")

            NetworkStatus.Error(e, e.toString() ?: "null message")
        }
    }

    suspend fun getWithBestCamera(
        limit: Int? = null,
        brand: String? = null
    ): NetworkStatus<List<PhoneDetail>> {
        return try {
            val token = authenticationDataSource.getToken()
            if (token.isNullOrEmpty()) return NetworkStatus.Error(
                FirebaseAuthException(
                    "404",
                    "Unauthorized: miss token or token is null"
                ), "token error"
            )

            val bearerToken = "Bearer $token"
            val data = apiService.getWithBestCamera(bearerToken, limit, brand).body()
            if (data.isNullOrEmpty()) return NetworkStatus.Error(
                null,
                "Error fetching data from server"
            )
            NetworkStatus.Success(data)
        } catch (e: Exception) {
            NetworkStatus.Error(e, e.message ?: "null message")
        }
    }

    suspend fun search(
        limit: Int? = null,
        search: String? = null
    ): NetworkStatus<List<PhoneDetail>> {
        return try {
            val token = authenticationDataSource.getToken()
            if (token.isNullOrEmpty()) return NetworkStatus.Error(
                FirebaseAuthException(
                    "404",
                    "Unauthorized: miss token or token is null"
                ), "token error"
            )

            val bearerToken = "Bearer $token"
            val data = apiService.search(bearerToken, limit, search).body()
            if (data.isNullOrEmpty()) return NetworkStatus.Error(
                null,
                "Error fetching data from server"
            )
            NetworkStatus.Success(data)
        } catch (e: Exception) {
            NetworkStatus.Error(e, e.message ?: "null message")
        }
    }

    private fun exceptionsHandler(responseBody: ResponseBody): NetworkStatus<List<PhoneDetail>> {
        return try {
            val errorBody = bodyToErrorApi(responseBody.string())
            when (errorBody.errorCode) {
                TIMEOUT -> NetworkStatus.Error(TimeoutException(), TIMEOUT)
                else -> NetworkStatus.Error(Exception(), "Unexpected error occurred")
            }
        } catch (e: Exception) {
            NetworkStatus.Error(e, "Unexpected error occurred")
        }
    }
}

