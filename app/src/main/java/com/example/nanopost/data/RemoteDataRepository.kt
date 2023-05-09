package com.example.nanopost.data

import androidx.paging.PagingData
import androidx.paging.map
import com.example.nanopost.data.paging.PostPagingSource
import com.example.nanopost.data.retrofit.NanoPostApiService
import com.example.nanopost.data.retrofit.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteDataRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
){

    suspend fun registerUser(username: String, password: String): String{
        return remoteDataSource.registerUser(username, password)
    }

    suspend fun authUser(username: String, password: String): String{
        return remoteDataSource.authUser(username, password)
    }

    fun getProfile(userId: String): Flow<Profile> {
        return remoteDataSource.getProfile(userId)
    }

    fun getProfilePosts(): Flow<PagingData<Post>> {
        return remoteDataSource.getProfilePosts()
    }
}