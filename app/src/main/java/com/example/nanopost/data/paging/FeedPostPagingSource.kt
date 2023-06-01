package com.example.nanopost.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.nanopost.data.retrofit.NanoPostApiService
import com.example.nanopost.data.retrofit.model.ApiPost

class FeedPostPagingSource(
    private val authService: NanoPostApiService,
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
            val response = authService.getFeed(
                count= params.loadSize,
                offset = params.key
            )
            LoadResult.Page(
                data=response.items,
                nextKey = response.offset,
                prevKey = null)
        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}