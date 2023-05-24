package com.example.nanopost.data

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import androidx.core.content.edit
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import coil.imageLoader
import coil.request.ImageRequest
import com.example.nanopost.NanoPostApp
import com.example.nanopost.R
import com.example.nanopost.data.paging.FeedPostPagingSource
import com.example.nanopost.data.paging.ImagesPagingSource
import com.example.nanopost.data.paging.PostPagingSource
import com.example.nanopost.data.paging.SearchPagingSource
import com.example.nanopost.data.retrofit.NanoPostApiService
import com.example.nanopost.data.retrofit.model.*
import com.example.nanopost.di.ApiService
import com.example.nanopost.di.AuthService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    @ApiService private val apiService: NanoPostApiService,
    @AuthService private val authService: NanoPostApiService,
    private val preferences: SharedPreferences,
) {

    suspend fun checkUsername(username: String): ResultResponseCheckUsername{
        return apiService.checkUsername(username)
    }

    suspend fun registerUser(username: String, password: String): String?{
        return try {
            apiService.registerUser(RegUser(
            username = username,
            password = password,
        )).apply {
            preferences.edit {
                putString("USERNAME", username)
                putString("USERID", userId)
                putString("TOKEN", token)
            }
        }.userId
        }catch (e: Exception){
            null
        }
    }

    suspend fun authUser(username: String, password: String): String?{
        return try {
            apiService.authUser(
            username = username,
            password = password,
            ).apply {
                preferences.edit {
                    putString("USERNAME", username)
                    putString("USERID", userId)
                    putString("TOKEN", token)
                }
            }.userId
        } catch (e: Exception){
            null
        }
    }

    fun getProfile(userId: String): Flow<Profile> {
        return flow{
            emit(authService.getUser(userId).toProfile())
        }
    }

    fun getPost(postId: String): Flow<Post> {
        return flow{
            emit(apiService.getPost(postId).toPost())
        }
    }

    fun getProfilePosts(profileId: String): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(pageSize = 5, enablePlaceholders = false),
            pagingSourceFactory = { PostPagingSource(apiService, profileId) }
        ).flow.map{ pagingData->
            pagingData.map { it.toPost() }
        }
    }

    fun getFeed(): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(pageSize = 5, enablePlaceholders = false),
            pagingSourceFactory = { FeedPostPagingSource(authService) }
        ).flow.map{ pagingData->
            pagingData.map { it.toPost() }
        }
    }

    fun getProfiles(query: String): Flow<PagingData<MiniProfile>> {
        return Pager(
            config = PagingConfig(pageSize = 30, enablePlaceholders = false),
            pagingSourceFactory = { SearchPagingSource(apiService, query) }
        ).flow.map{ pagingData->
            pagingData.map { it.toMiniProfile() }
        }
    }

    fun getProfileImages(profileId: String): Flow<PagingData<ImageData>> {
        return Pager(
            config = PagingConfig(pageSize = 5, enablePlaceholders = false),
            pagingSourceFactory = { ImagesPagingSource(apiService, profileId) }
        ).flow.map{ pagingData->
            pagingData.map { it.toImageData() }
        }
    }

    fun getImageByUrl(imageUrl: String, width: Int, height: Int, context: Context): Flow<Drawable?>{
        return flow{
            val imageRequest = ImageRequest.Builder(context)
                .size(width, height)
                .data(imageUrl)
                .build()
            emit(context.imageLoader.execute(imageRequest).drawable)
        }
    }

    fun getImageById(imageId: String): Flow<ImageData>{
        return flow{
            emit(apiService.getImage(imageId).toImageData())
        }
    }

    fun checkToken() = if(preferences.contains("TOKEN") && preferences.contains("USERNAME")){
        preferences.getString("USERNAME", null)
    } else {
        null
    }

    fun subscribeToUser(username: String) =
        flow{
            emit(authService.subscribe(username).result)
        }

    fun unsubscribeFromUser(username: String) =
        flow{
            emit(authService.unsubscribe(username).result)
        }

    fun removeAccountData(){
        preferences.edit {
            remove("USERID")
            remove("USERNAME")
            remove("TOKEN")
        }
    }

    fun checkSavedUsername(username: String) = preferences.getString("USERNAME", null) == username

    fun getSavedUsername() = preferences.getString("USERNAME", null)

}