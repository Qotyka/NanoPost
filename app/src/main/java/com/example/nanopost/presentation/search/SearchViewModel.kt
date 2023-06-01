package com.example.nanopost.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.nanopost.data.retrofit.model.MiniProfile
import com.example.nanopost.data.retrofit.model.Post
import com.example.nanopost.data.retrofit.model.ProfileCompact
import com.example.nanopost.domain.GetPostUseCase
import com.example.nanopost.domain.GetProfilesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getProfilesUseCase: GetProfilesUseCase,
): androidx.lifecycle.ViewModel() {

    private val _profiles = MutableLiveData<PagingData<MiniProfile>>()
    val profiles: LiveData<PagingData<MiniProfile>> = _profiles

    fun getProfiles(query: String){
        viewModelScope.launch {
            _profiles.value = getProfilesUseCase(query).first()
        }
    }

}