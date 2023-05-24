package com.example.nanopost.domain

import androidx.paging.PagingData
import com.example.nanopost.data.RemoteDataRepository
import com.example.nanopost.data.retrofit.model.PagedDataResponse
import com.example.nanopost.data.retrofit.model.Profile
import com.example.nanopost.data.retrofit.model.ProfileCompact
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfilesUseCase @Inject constructor(
    private val repository: RemoteDataRepository,
) {

    operator fun invoke(query: String) =
        repository.getProfiles(query)
}