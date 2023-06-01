package com.example.nanopost.domain

import com.example.nanopost.data.RemoteDataRepository
import com.example.nanopost.data.retrofit.model.CreatePostData
import javax.inject.Inject

class CreatePostUseCase @Inject constructor(
    private val repository: RemoteDataRepository,
) {
    operator fun invoke(post: CreatePostData) = repository.createPost(post)
}