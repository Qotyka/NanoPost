package com.example.nanopost.domain

import com.example.nanopost.data.RemoteDataRepository
import com.example.nanopost.data.retrofit.model.Profile
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedUsernameUseCase @Inject constructor(
    private val repository: RemoteDataRepository,
) {

    operator fun invoke() = repository.getSavedUsername()
}