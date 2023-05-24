package com.example.nanopost.presentation.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nanopost.data.retrofit.model.Post
import com.example.nanopost.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val getPostDataUseCase: GetPostUseCase,
): androidx.lifecycle.ViewModel() {

    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post> = _post

    fun getPost(postId: String){
        viewModelScope.launch {
            _post.value = getPostDataUseCase(postId).first()
        }
    }

}