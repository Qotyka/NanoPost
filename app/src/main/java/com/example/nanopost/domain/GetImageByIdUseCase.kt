package com.example.nanopost.domain

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.paging.PagingData
import com.example.nanopost.data.RemoteDataRepository
import com.example.nanopost.data.retrofit.model.ImageData
import com.example.nanopost.data.retrofit.model.Post
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImageByIdUseCase @Inject constructor(
    private val repository: RemoteDataRepository,
) {

    operator fun invoke(imageId: String): Flow<ImageData> {
        return repository.getImageById(imageId)
    }
}