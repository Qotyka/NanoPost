package com.example.nanopost.domain

import androidx.paging.PagingData
import com.example.nanopost.data.RemoteDataRepository
import com.example.nanopost.data.retrofit.model.Post
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfilePostsUseCase@Inject constructor(
    private val repository: RemoteDataRepository,
) {

    operator fun invoke(): Flow<PagingData<Post>> {
        return repository.getProfilePosts()
    }
}