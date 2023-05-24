package com.example.nanopost.domain

import com.example.nanopost.data.RemoteDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoveAccountDataUseCase @Inject constructor(
    private val repository: RemoteDataRepository,
) {

    operator fun invoke() = repository.removeAccountData()
}