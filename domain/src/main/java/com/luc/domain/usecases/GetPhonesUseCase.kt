package com.luc.domain.usecases

import com.luc.common.NetworkStatus
import com.luc.common.model.phonespecs.PhoneDetail
import com.luc.domain.ApiServiceRepository

class GetPhonesUseCase(private val apiServiceRepository: ApiServiceRepository) {

    suspend fun getLatestPhones(limit: Int? = null): NetworkStatus<List<PhoneDetail>> {
        return apiServiceRepository.getLatestPhones(limit)
    }

    suspend fun getWithBestCamera(
        limit: Int? = null,
        brand: String? = null
    ): NetworkStatus<List<PhoneDetail>> {
        return apiServiceRepository.getWithBestCamera(limit, brand)
    }
}
