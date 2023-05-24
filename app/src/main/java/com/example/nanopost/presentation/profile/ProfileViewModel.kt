package com.example.nanopost.presentation.profile

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.nanopost.data.retrofit.model.ImageData
import com.example.nanopost.data.retrofit.model.Post
import com.example.nanopost.data.retrofit.model.Profile
import com.example.nanopost.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val getImageByIdUseCase: GetImageByIdUseCase,
    private val getImageByUrlUseCase: GetImageByUrlUseCase,
    private val getProfilePostsUseCase: GetProfilePostsUseCase,
    private val subscribeUseCase: SubscribeToUserUseCase,
    private val unsubscribeUseCase: UnsubscribeFromUserUseCase,
    private val removeAccountDataUseCase: RemoveAccountDataUseCase,
): androidx.lifecycle.ViewModel() {

    private val _profile = MutableLiveData<Profile>()
    val profile: LiveData<Profile> = _profile

    private val _posts = MutableLiveData<PagingData<Post>>()
    val posts: LiveData<PagingData<Post>> = _posts

    private val _subscribe = MutableLiveData<Boolean>()
    val subscribe: LiveData<Boolean> = _subscribe

    private val _unsubscribe = MutableLiveData<Boolean>()
    val unsubscribe: LiveData<Boolean> = _unsubscribe

    fun getProfile(profileId: String){
        viewModelScope.launch {
            _profile.value = getProfileUseCase(profileId).first()
        }
    }

    fun getProfilePosts(profileId: String){
        viewModelScope.launch {
            _posts.value = getProfilePostsUseCase(profileId).first()
        }
    }

    fun subscribe(username: String){
        viewModelScope.launch {
            _subscribe.value = subscribeUseCase(username).first()
        }
    }

    fun unsubscribe(username: String){
        viewModelScope.launch {
            _subscribe.value = unsubscribeUseCase(username).first()
        }
    }

    fun logout(){
        removeAccountDataUseCase()
    }

    override fun onCleared() {
        super.onCleared()
    }
}