package com.luc.presentation.viewmodel

import android.util.Log
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

    private val _isFetchingPhones = MutableLiveData<Boolean>()
    val isFetchingPhones: LiveData<Boolean> = _isFetchingPhones

    private val _isFetchingPhonesBestCamera = MutableLiveData<Boolean>()
    val isFetchingPhonesBestCamera: LiveData<Boolean> = _isFetchingPhonesBestCamera

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getData() {
        if (_bestCameraPhones.value.isNullOrEmpty() || _latestPhones.value.isNullOrEmpty()) {
            viewModelScope.launch {
                _isFetchingPhones.value = true
                _error.value = ""
                val latestPhones = async { getPhonesUseCase.getLatestPhones() }
                val bestCameraPhones = async { getPhonesUseCase.getWithBestCamera() }

                val (resLatest, resBest) = awaitAll(latestPhones, bestCameraPhones)

                if (resLatest is NetworkStatus.Success && resBest is NetworkStatus.Success) {
                    _latestPhones.value = resLatest.data!!
                    _bestCameraPhones.value = resBest.data!!
                } else {
                    _error.value = getError(resLatest, resBest)
                }

                _isFetchingPhones.value = false
            }
        }
    }

    fun setBestCameraBrand(brand: String?) {
        _isFetchingPhonesBestCamera.value = true
        viewModelScope.launch {
            when (val data = getPhonesUseCase.getWithBestCamera(brand = brand)) {
                is NetworkStatus.Error -> _error.value = getError(data)
                is NetworkStatus.Success -> _bestCameraPhones.value = data.data!!
            }
            _isFetchingPhonesBestCamera.value = false
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