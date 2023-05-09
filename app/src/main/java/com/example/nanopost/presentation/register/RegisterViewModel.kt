package com.example.nanopost.presentation.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nanopost.data.retrofit.model.TokenResponse
import com.example.nanopost.domain.AuthUserUseCase
import com.example.nanopost.domain.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val authUserUseCase: AuthUserUseCase,
): androidx.lifecycle.ViewModel() {

    private val _responses = MutableLiveData<String>()
    val responses = _responses

    fun authUser(username: String, password: String){
        viewModelScope.launch {
            _responses.value = authUserUseCase(username, password).first()
        }
    }

    fun registerUser(username: String, password: String) {
        viewModelScope.launch {
            _responses.value = registerUserUseCase(username, password).first()
        }
    }

    override fun onCleared(){
        super.onCleared()
    }
}