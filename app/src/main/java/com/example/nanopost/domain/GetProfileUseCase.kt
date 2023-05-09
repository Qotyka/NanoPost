package com.example.nanopost.domain

import androidx.paging.PagingData
import com.example.nanopost.data.RemoteDataRepository
import com.example.nanopost.data.retrofit.model.Profile
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val repository: RemoteDataRepository,
) {

    operator fun invoke(userId: String): Flow<Profile> {
        return repository.getProfile(userId)
    }
}