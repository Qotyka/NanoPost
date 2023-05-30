package com.example.nanopost.domain

import com.example.nanopost.data.RemoteDataRepository
import javax.inject.Inject

class DeleteImageUseCase @Inject constructor(
    private val repository: RemoteDataRepository,
) {
    operator fun invoke(imageId: String) = repository.deleteImage(imageId)
}