package com.luc.data.remote.api

import com.luc.common.model.phonespecs.PhoneDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET("latest-releases")
    suspend fun getLatestPhones(
        @Header("Authorization") token: String,
        @Query("limit") limit: Int? = null
    ): Response<List<PhoneDetail>>

    @GET("best-camera")
    suspend fun getWithBestCamera(
        @Header("Authorization") token: String,
        @Query("limit") limit: Int? = null,
        @Query("brand") brand: String? = null
    ): Response<List<PhoneDetail>>
}