package com.example.nanopost.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.nanopost.data.retrofit.NanoPostApiService
import com.example.nanopost.data.retrofit.model.ApiImage
import com.example.nanopost.data.retrofit.model.ApiPost

class ImagesPagingSource (
    private val apiService: NanoPostApiService,
    private val profileId: String
) : PagingSource<String, ApiImage>() {
    override fun getRefreshKey(
        state: PagingState<String, ApiImage>
    ): String? {
        return null
    }

    override suspend fun load(
        params: LoadParams<String>
    ): LoadResult<String, ApiImage> {
        return try{
            val response = apiService.getProfileImages(
                profileId = profileId,
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