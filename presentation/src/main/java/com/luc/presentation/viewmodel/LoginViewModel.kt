package com.luc.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.luc.common.NetworkStatus
import com.luc.common.model.UserProfile
import com.luc.common.model.phonespecs.PhoneDetail
import com.luc.domain.usecases.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    var googleSignInClient: GoogleSignInClient? = null

    private val _navigateToHome = MutableLiveData<UserProfile>()
    val navigateToHome: LiveData<UserProfile> = _navigateToHome

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _showError = MutableLiveData<String>()
    val showError: LiveData<String> = _showError

    fun signUpWithEmailAndPassword(fullName: String, email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = loginUseCase.signUpWithEmailAndPassword(fullName, email, password)) {
                is NetworkStatus.Success -> _navigateToHome.value = result.data!!
                is NetworkStatus.Error -> _showError.value = result.errorCode
            }
            _isLoading.value = true
        }
    }

    fun signInWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = loginUseCase.signInWithEmailAndPassword(email, password)) {
                is NetworkStatus.Success -> _navigateToHome.value = result.data!!
                is NetworkStatus.Error -> _showError.value = result.errorCode
            }
            _isLoading.value = true
        }
    }

    fun signInAnonymous() {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = loginUseCase.signInAnonymous()) {
                is NetworkStatus.Success -> _navigateToHome.value = result.data!!
                is NetworkStatus.Error -> _showError.value = result.errorCode
            }
            _isLoading.value = true
        }
    }

    fun signInWithGoogle(token: String) {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = loginUseCase.signInWithGoogle(token)) {
                is NetworkStatus.Success -> _navigateToHome.value = result.data!!
                is NetworkStatus.Error -> _showError.value = result.errorCode
            }
            _isLoading.value = true
        }

        fun getUserLogged() = liveData {
            emit(loginUseCase.getUserLogged())
        }

        fun signOut() {
            googleSignInClient?.signOut()
            loginUseCase.signOut()
        }
    }

}