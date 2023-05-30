package com.example.nanopost.data

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.core.content.edit
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.drawable.toIcon
import androidx.core.net.toFile
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
import kotlinx.serialization.json.JsonNull.content
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.create
import okhttp3.RequestBody.Companion.toRequestBody
import okio.ByteString
import okio.ByteString.Companion.readByteString
import okio.ByteString.Companion.toByteString
import retrofit2.http.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

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

    fun uploadImage(image: String) = flow{
        emit(authService.uploadImage(image).toImageData())
    }

    fun createPost(post: CreatePostData) = flow{
        val text = post.text?.let { text ->
            MultipartBody.Part.createFormData(
                "text",
                text,
            )
        }
        val images = mutableListOf<MultipartBody.Part?>(null, null, null, null)
        for(i in 0 until post.images.size){
            val byteArrayOutput = ByteArrayOutputStream()
            post.images[i].compress(CompressFormat.JPEG, 0, byteArrayOutput)
            images[i] = MultipartBody.Part.createFormData(
                "image${i+1}",
                "image${i+1}",
                byteArrayOutput.toByteArray().toByteString().toRequestBody("image/*".toMediaType())
            )
            println("OK")
        }

        emit(authService.createPost(
            text,
            images[0],
            images[1],
            images[2],
            images[3],
        ).toPost())
    }

    fun deletePost(postId: String) = flow {
        emit(authService.deletePost(postId).result)
    }

    fun deleteImage(imageId: String) = flow{
        emit(authService.deleteImage(imageId))
    }

    fun editProfile(
        profileId: String,
        profileData: EditProfileData,
    ) = flow {
        emit(authService.editProfile(profileId, profileData).result)
    }

}