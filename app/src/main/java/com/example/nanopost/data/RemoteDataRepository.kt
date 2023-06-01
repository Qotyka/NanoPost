package com.example.nanopost.data

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.nanopost.data.paging.ImagesPagingSource
import com.example.nanopost.data.paging.PostPagingSource
import com.example.nanopost.data.retrofit.NanoPostApiService
import com.example.nanopost.data.retrofit.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteDataRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
){

    suspend fun registerUser(username: String, password: String): String?{
        return remoteDataSource.registerUser(username, password)
    }

    suspend fun checkUsername(username: String) = remoteDataSource.checkUsername(username).result

    fun subscribeToUser(username: String) = remoteDataSource.subscribeToUser(username)

    fun unsubscribeFromUser(username: String) = remoteDataSource.unsubscribeFromUser(username)

    suspend fun authUser(username: String, password: String): String?{
        return remoteDataSource.authUser(username, password)
    }

    fun getProfile(userId: String) = remoteDataSource.getProfile(userId)

    fun getPost(postId: String) = remoteDataSource.getPost(postId)

    fun getProfilePosts(profileId: String): Flow<PagingData<Post>> {
        return remoteDataSource.getProfilePosts(profileId)
    }

    fun getFeed(): Flow<PagingData<Post>> {
        return remoteDataSource.getFeed()
    }

    fun getProfileImages(profileId: String) = remoteDataSource.getProfileImages(profileId)

    fun getImageById(imageId: String): Flow<ImageData>{
        return remoteDataSource.getImageById(imageId)
    }

    fun getImageByUrl(imageUrl: String, width: Int, height: Int, context: Context): Flow<Drawable?>{
        return remoteDataSource.getImageByUrl(imageUrl, width, height, context)
    }

    fun checkToken() = remoteDataSource.checkToken()

    fun removeAccountData() = remoteDataSource.removeAccountData()

    fun checkSavedUsername(username: String) = remoteDataSource.checkSavedUsername(username)

    fun getSavedUsername() = remoteDataSource.getSavedUsername()

    fun getProfiles(query: String) = remoteDataSource.getProfiles(query)

    fun uploadImage(image: String) = remoteDataSource.uploadImage(image)

    fun createPost(post: CreatePostData) = remoteDataSource.createPost(post)

    fun deletePost(postId: String) = remoteDataSource.deletePost(postId)

    fun deleteImage(imageId: String) = remoteDataSource.deleteImage(imageId)

    fun editProfile(
        profileId: String,
        profileData: EditProfileData,
    ) = remoteDataSource.editProfile(
        profileId,
        profileData,
    )

}