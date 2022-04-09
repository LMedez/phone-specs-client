package com.luc.data.remote.api

import com.luc.common.model.ErrorApi

class ApiServiceException(errorApi: ErrorApi): Exception() {
    val errorDetail: ErrorApi = errorApi
}