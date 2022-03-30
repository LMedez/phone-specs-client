package com.luc.presentation.viewmodel

import androidx.lifecycle.*
import com.luc.common.NetworkStatus
import com.luc.common.model.phonespecs.PhoneDetail
import com.luc.domain.usecases.GetPhonesUseCase
import kotlinx.coroutines.*

class PhonesViewModel(private val getPhonesUseCase: GetPhonesUseCase) : ViewModel() {

    private val _latestPhones = MutableLiveData<List<PhoneDetail>>()
    val latestPhones: LiveData<List<PhoneDetail>> = _latestPhones

    private val _bestCameraPhones = MutableLiveData<List<PhoneDetail>>()
    val bestCameraPhones: LiveData<List<PhoneDetail>> = _bestCameraPhones

    private val _isFetchingData = MutableLiveData<Boolean>()
    val isFetchingData: LiveData<Boolean> = _isFetchingData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        viewModelScope.launch {
            _isFetchingData.value = true
            val latestPhones = async { getPhonesUseCase.getLatestPhones() }
            val bestCameraPhones = async { getPhonesUseCase.getWithBestCamera() }

            val (resLatest, resBest) = awaitAll(latestPhones, bestCameraPhones)

            if (resLatest is NetworkStatus.Success && resBest is NetworkStatus.Success) {
                _isFetchingData.value = false
                _latestPhones.value = resLatest.data!!
                _bestCameraPhones.value = resBest.data!!
            } else {
                _isFetchingData.value = false
                _error.value = getError(resLatest, resBest)
            }
        }
    }

    private fun getError(vararg networkStatus: NetworkStatus<List<PhoneDetail>>): String {
        var result = ""
        networkStatus.forEach {
            if (it is NetworkStatus.Error) {
                result = it.message
            }
        }
        return result
    }
}