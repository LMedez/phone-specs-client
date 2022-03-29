package com.luc.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.luc.common.NetworkStatus
import com.luc.domain.usecases.GetPhonesUseCase

class PhonesViewModel(private val getPhonesUseCase: GetPhonesUseCase): ViewModel() {

    fun getLatestPhones(limit: Int? = null) = liveData {
        emit(NetworkStatus.Loading)
        emit(getPhonesUseCase.getLatestPhones(limit))
    }

    fun getWithBestCamera(limit: Int? = null, brand: String? = null) = liveData {
        emit(NetworkStatus.Loading)
        emit(getPhonesUseCase.getWithBestCamera(limit, brand))
    }
}