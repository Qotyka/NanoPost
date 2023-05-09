package com.example.nanopost.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.nanopost.data.retrofit.NanoPostApiService
import com.example.nanopost.data.retrofit.model.ApiPost

class PostPagingSource(
    private val apiService: NanoPostApiService,
) : PagingSource<String, ApiPost>() {
    override fun getRefreshKey(
        state: PagingState<String, ApiPost>
    ): String? {
        return null
    }

    override suspend fun load(
        params: LoadParams<String>
    ): LoadResult<String, ApiPost> {
        return try{
        val response = apiService.getProfilePosts(
            profileId = "evo",
            count= params.loadSize,
            offset = params.key)
        LoadResult.Page(
            data=response.items,
            nextKey = response.offset,
            prevKey = null)
        } catch (e: Exception){
            return LoadResult.Error(e)
        }
    }
}