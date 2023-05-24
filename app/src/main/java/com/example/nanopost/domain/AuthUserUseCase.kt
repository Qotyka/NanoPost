package com.example.nanopost.domain

import androidx.paging.PagingData
import com.example.nanopost.data.RemoteDataRepository
import com.example.nanopost.data.retrofit.model.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthUserUseCase @Inject constructor(
    private val repository: RemoteDataRepository,
) {

    operator fun invoke(username: String, password: String): Flow<String?> {
        return flow{
            emit(repository.authUser(username, password))
        }
    }
}