package com.example.nanopost.data

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.paging.Pager
import androidx.paging.PagingConfig
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

    fun getProfilePosts(profileId: String): Flow<PagingData<Post>> {
        return remoteDataSource.getProfilePosts(profileId)
    }

    fun getImageById(imageId: String): Flow<ImageData>{
        return remoteDataSource.getImageById(imageId)
    }

    fun getImageByUrl(imageUrl: String, width: Int, height: Int, context: Context): Flow<Drawable?>{
        return remoteDataSource.getImageByUrl(imageUrl, width, height, context)
    }
}