package com.luc.data.remote.api

import com.luc.common.model.phonespecs.PhoneDetail
import retrofit2.http.GET

interface ApiService {
    @GET("phone")
    suspend fun getPhoneDetail() : PhoneDetail
}