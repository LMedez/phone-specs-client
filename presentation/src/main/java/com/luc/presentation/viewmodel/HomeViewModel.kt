package com.luc.presentation.viewmodel

import androidx.lifecycle.*
import com.luc.common.Event
import com.luc.common.NetworkStatus
import com.luc.common.model.phonespecs.PhoneDetail
import com.luc.domain.usecases.GetPhonesUseCase
import com.luc.domain.usecases.LATEST_PHONES
import com.luc.domain.usecases.WITH_BEST_CAMERA
import kotlinx.coroutines.launch

class HomeViewModel(private val getPhonesUseCase: GetPhonesUseCase) : ViewModel() {


    private val _getPhones = MutableLiveData<Map<String, List<PhoneDetail>>>()
    val getPhones: LiveData<Map<String, List<PhoneDetail>>> = _getPhones

    private val _isFetchingPhones = MutableLiveData<Boolean>()
    val isFetchingPhones: LiveData<Boolean> = _isFetchingPhones

    private val _isFetchingPhonesBestCamera = MutableLiveData<Boolean>()
    val isFetchingPhonesBestCamera: LiveData<Boolean> = _isFetchingPhonesBestCamera

    private val _navigateToError = MutableLiveData<Event<String>>()
    val navigateToError: LiveData<Event<String>> = _navigateToError

    private val _showErrorOnChangeBrand = MutableLiveData<Event<String>>()
    val showErrorOnChangeBrand: LiveData<Event<String>> = _showErrorOnChangeBrand

    fun getPhones() {
        _isFetchingPhones.value = true
        viewModelScope.launch {

            val phoneData = getPhonesUseCase.getLatestAndBest()
            when {
                phoneData[LATEST_PHONES] is NetworkStatus.Error -> {
                    _navigateToError.value =
                        Event((phoneData[LATEST_PHONES] as NetworkStatus.Error).errorCode)
                }
                phoneData[WITH_BEST_CAMERA] is NetworkStatus.Error -> {
                    _navigateToError.value =
                        Event((phoneData[WITH_BEST_CAMERA] as NetworkStatus.Error).errorCode)
                }
                else ->
                    _getPhones.value = mapOf(
                        LATEST_PHONES to (phoneData[LATEST_PHONES] as NetworkStatus.Success).data,
                        WITH_BEST_CAMERA to (phoneData[WITH_BEST_CAMERA] as NetworkStatus.Success).data
                    )
            }
            _isFetchingPhones.value = false
        }
    }

    fun setBestCameraBrand(brand: String?) {
        _isFetchingPhonesBestCamera.value = true
        viewModelScope.launch {
            when (val data = getPhonesUseCase.getWithBestCamera(brand = brand)) {
                is NetworkStatus.Error -> _showErrorOnChangeBrand.value = Event(getError(data))
                is NetworkStatus.Success -> _getPhones.value = mapOf(
                    LATEST_PHONES to _getPhones.value?.get(LATEST_PHONES)!!,
                    WITH_BEST_CAMERA to data.data
                )
            }
            _isFetchingPhonesBestCamera.value = false
        }
    }

    private fun getError(vararg networkStatus: NetworkStatus<List<PhoneDetail>>): String {
        var result = ""
        networkStatus.forEach {
            if (it is NetworkStatus.Error) {
                result = it.errorCode
            }
        }
        return result
    }
}