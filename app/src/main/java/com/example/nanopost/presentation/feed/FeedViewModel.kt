package com.example.nanopost.presentation.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.nanopost.data.retrofit.model.Post
import com.example.nanopost.data.retrofit.model.Profile
import com.example.nanopost.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val removeAccountDataUseCase: RemoveAccountDataUseCase,
    private val getFeedUseCase: GetFeedUseCase,
): androidx.lifecycle.ViewModel() {

    private val _posts = MutableLiveData<PagingData<Post>>()
    val posts: LiveData<PagingData<Post>> = _posts

    fun getFeed() {
        viewModelScope.launch {
            _posts.value = getFeedUseCase().first()
        }
    }
}