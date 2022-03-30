package com.luc.data.remote.api

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.luc.common.NetworkStatus
import com.luc.common.model.phonespecs.PhoneDetail
import com.luc.data.remote.firebase.auth.AuthenticationDataSource
import com.luc.data.remote.firebase.firestore.FirestoreData

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
            val data = apiService.getLatestPhones(bearerToken, limit).body()
            if (data.isNullOrEmpty()) return NetworkStatus.Error(
                null,
                "Error fetching data from server"
            )
            NetworkStatus.Success(data)
        } catch (e: Exception) {
            NetworkStatus.Error(e, e.message ?: "null message")
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

}