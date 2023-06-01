package com.example.nanopost.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nanopost.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val authUserUseCase: AuthUserUseCase,
    private val checkUserNameUseCase: CheckUsernameUseCase,
    private val checkTokenUseCase: CheckTokenUseCase,
    private val unsubscribeUseCase: UnsubscribeFromUserUseCase,
    private val checkSavedUsernameUseCase: CheckSavedUsernameUseCase,
): androidx.lifecycle.ViewModel() {

    private val _checkUsernameResponses = MutableLiveData<String>()
    val checkUsernameResponses: LiveData<String> = _checkUsernameResponses

    private val _registerResponses = MutableLiveData<String?>()
    val registerResponses: LiveData<String?> = _registerResponses

    private val _authResponses = MutableLiveData<String?>()
    val authResponses: LiveData<String?> = _authResponses

    fun authUser(username: String, password: String){
        viewModelScope.launch {
            _authResponses.value = authUserUseCase(username, password).first()
        }
    }

    fun registerUser(username: String, password: String) {
        viewModelScope.launch {
            _registerResponses.value = registerUserUseCase(username, password).first()
        }
    }
    fun checkUsername(username: String) {
        viewModelScope.launch {
            when(val response = checkUserNameUseCase(username).first()){
                RESPONSE_TOO_SHORT -> _checkUsernameResponses.value = "Username must be longer than 3 characters"
                RESPONSE_TOO_LONG -> _checkUsernameResponses.value = "Username must be shorter than 16 characters"
                RESPONSE_INVALID_CHARACTERS -> _checkUsernameResponses.value = "Username must contain only characters like a-z,_, ."
                RESPONSE_TAKEN -> _checkUsernameResponses.value = response
                else -> _checkUsernameResponses.value = response
            }
        }
    }

    fun checkPassword(password: String) = password.length >= 8

    fun checkToken() = checkTokenUseCase()

    fun checkSavedUsername(username: String) = checkSavedUsernameUseCase(username)

    override fun onCleared(){
        super.onCleared()
    }

    companion object{
        const val RESPONSE_TOO_SHORT = "TooShort"
        const val RESPONSE_TOO_LONG = "TooLong"
        const val RESPONSE_INVALID_CHARACTERS = "InvalidCharacters"
        const val RESPONSE_TAKEN = "Taken"
        const val RESPONSE_FREE = "Free"
    }
}