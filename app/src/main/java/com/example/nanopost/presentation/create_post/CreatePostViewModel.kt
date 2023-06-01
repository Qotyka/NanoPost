package com.example.nanopost.presentation.create_post

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nanopost.data.retrofit.model.CreatePostData
import com.example.nanopost.data.retrofit.model.EditProfileData
import com.example.nanopost.data.retrofit.model.Post
import com.example.nanopost.domain.CreatePostUseCase
//import com.example.nanopost.domain.CreatePostUseCase
import com.example.nanopost.domain.EditProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(
    private val createPostUseCase: CreatePostUseCase,
): androidx.lifecycle.ViewModel() {

    private val _images = MutableLiveData<List<Uri>>()
    val images: LiveData<List<Uri>> = _images

    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post> = _post

    fun createPost(post: CreatePostData) {
        viewModelScope.launch {
            _post.value = createPostUseCase(post).first()
        }
    }

    fun getImages(images: List<Uri>) {
        viewModelScope.launch {
            _images.value = images
        }
    }
}