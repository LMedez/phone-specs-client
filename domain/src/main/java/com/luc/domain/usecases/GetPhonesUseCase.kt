package com.luc.domain.usecases

import com.luc.common.NetworkStatus
import com.luc.common.model.phonespecs.PhoneDetail
import com.luc.domain.ApiServiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

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

    suspend fun getLatestAndBest(): Map<String, NetworkStatus<List<PhoneDetail>>> {
        return withContext(Dispatchers.IO) {
            val latestPhones = async { getLatestPhones() }
            val bestCameraPhones = async { getWithBestCamera() }
            val (resLatest, resBest) = awaitAll(latestPhones, bestCameraPhones)
            mapOf(LATEST_PHONES to resLatest, WITH_BEST_CAMERA to resBest)
        }
    }
}

const val LATEST_PHONES = "latestPhones"
const val WITH_BEST_CAMERA = "withBestCamera"
