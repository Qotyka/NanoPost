package com.example.nanopost.domain

import androidx.paging.PagingData
import com.example.nanopost.data.RemoteDataRepository
import com.example.nanopost.data.retrofit.model.ImageData
import com.example.nanopost.data.retrofit.model.Post
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileImagesUseCase @Inject constructor(
    private val repository: RemoteDataRepository,
) {

    operator fun invoke(profileId: String): Flow<PagingData<ImageData>> {
        return repository.getProfileImages(profileId)
    }
}