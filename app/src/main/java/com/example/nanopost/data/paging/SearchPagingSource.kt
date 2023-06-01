package com.example.nanopost.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.util.query
import com.example.nanopost.data.retrofit.NanoPostApiService
import com.example.nanopost.data.retrofit.model.ApiPost
import com.example.nanopost.data.retrofit.model.ProfileCompact

class SearchPagingSource (
    private val apiService: NanoPostApiService,
    private val query: String
) : PagingSource<String, ProfileCompact>() {
    override fun getRefreshKey(
        state: PagingState<String, ProfileCompact>
    ): String? {
        return null
    }

    override suspend fun load(
        params: LoadParams<String>
    ): LoadResult<String, ProfileCompact> {
        return try{
            val response = apiService.getProfiles(
                count= params.loadSize,
                offset = params.key,
                query = query
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