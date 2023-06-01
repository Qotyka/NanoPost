package com.example.nanopost.presentation.images

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.nanopost.data.retrofit.model.ImageData
import com.example.nanopost.data.retrofit.model.Post
import com.example.nanopost.domain.GetProfileImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val getProfileImagesUseCase: GetProfileImagesUseCase
): androidx.lifecycle.ViewModel() {

    private val _images = MutableLiveData<PagingData<ImageData>>()
    val images: LiveData<PagingData<ImageData>> = _images

    fun getImages(profileId: String){
        viewModelScope.launch {
            _images.value = getProfileImagesUseCase(profileId).first()
        }
    }

}