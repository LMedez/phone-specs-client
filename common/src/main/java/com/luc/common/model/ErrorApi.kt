package com.luc.common.model

import com.google.gson.Gson

import java.time.ZonedDateTime

data class ErrorApi(
    val dateTime: ZonedDateTime,
    val httpStatusCode: String,
    val errorCode: String,
    val message: String,
    val details: String
)

fun bodyToErrorApi(bodyError: String): ErrorApi {
    return Gson().fromJson(bodyError, ErrorApi::class.java)
}