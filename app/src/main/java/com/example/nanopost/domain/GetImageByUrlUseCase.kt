package com.example.nanopost.domain

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.paging.PagingData
import com.example.nanopost.data.RemoteDataRepository
import com.example.nanopost.data.retrofit.model.Post
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImageByUrlUseCase @Inject constructor(
    private val repository: RemoteDataRepository,
) {

    operator fun invoke(imageUrl: String,  width: Int, height: Int, context: Context): Flow<Drawable?> {
        return repository.getImageByUrl(imageUrl, width, height, context)
    }
}