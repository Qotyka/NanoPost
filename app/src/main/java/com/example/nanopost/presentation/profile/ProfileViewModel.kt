package com.example.nanopost.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nanopost.data.retrofit.model.Profile
import com.example.nanopost.domain.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
): androidx.lifecycle.ViewModel() {

    private val _profile = MutableLiveData<Profile>()
    val profile = _profile

    fun getProfile(userId: String){
        viewModelScope.launch {
            _profile.value = getProfileUseCase(userId).first()
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}