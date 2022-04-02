package com.luc.presentation.viewmodel

import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luc.common.NetworkStatus
import com.luc.common.model.UserProfile
import com.luc.common.model.phonespecs.PhoneDetail
import com.luc.domain.usecases.GetPhonesUseCase
import com.luc.domain.usecases.LATEST_PHONES
import com.luc.domain.usecases.LoginUseCase
import com.luc.domain.usecases.WITH_BEST_CAMERA
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.log

class SplashScreenViewModel(
    private val loginUseCase: LoginUseCase,
    private val getPhonesUseCase: GetPhonesUseCase
) : ViewModel() {

    private val _navigateToHome = MutableLiveData<UserProfile>()
    val navigateToHome: LiveData<UserProfile> = _navigateToHome

    private val _navigateToLogin = MutableLiveData<Nothing>()
    val navigateToLogin: LiveData<Nothing> = _navigateToLogin

    init {
        viewModelScope.launch {
            val currentUser = loginUseCase.getUserLogged()
            if (currentUser != null) {
               _navigateToHome.value = currentUser!!
            } else _navigateToLogin.forceRefresh()
        }
    }
}

fun <T> MutableLiveData<T>.forceRefresh() {
    this.value = this.value
}
