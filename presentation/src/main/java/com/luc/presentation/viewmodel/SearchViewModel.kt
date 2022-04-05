package com.luc.presentation.viewmodel

import androidx.lifecycle.*
import com.luc.common.NetworkStatus
import com.luc.common.model.phonespecs.PhoneDetail
import com.luc.domain.usecases.GetPhonesUseCase
import kotlinx.coroutines.launch

class SearchViewModel(private val getPhonesUseCase: GetPhonesUseCase) : ViewModel() {

    private val _showErrorMessage = MutableLiveData<String>()
    val showErrorMessage: LiveData<String> = _showErrorMessage

    private val _isSearching = MutableLiveData<Boolean>()
    val isSearching: LiveData<Boolean> = _isSearching

    private val _searchResult = MutableLiveData<List<PhoneDetail>>()
    val searchResult: LiveData<List<PhoneDetail>> = _searchResult

    fun search(query: String) = viewModelScope.launch {
        _isSearching.value = true
        when (val searchResult = getPhonesUseCase.search(search = query)) {
            is NetworkStatus.Success -> _searchResult.value = searchResult.data!!
            is NetworkStatus.Error -> _showErrorMessage.value = searchResult.errorCode
        }
        _isSearching.value = false
    }
}