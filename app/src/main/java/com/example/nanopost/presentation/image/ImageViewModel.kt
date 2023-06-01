package com.example.nanopost.presentation.image

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.nanopost.data.retrofit.model.ImageData
import com.example.nanopost.domain.GetImageByUrlUseCase
import com.example.nanopost.domain.GetProfileImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val getImageByUrlUseCase: GetImageByUrlUseCase
): androidx.lifecycle.ViewModel() {

}