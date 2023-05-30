package com.example.nanopost.domain

import com.example.nanopost.data.RemoteDataRepository
import com.example.nanopost.data.retrofit.model.EditProfileData
import javax.inject.Inject

class EditProfileUseCase @Inject constructor(
    private val repository: RemoteDataRepository,
) {
    operator fun invoke(profileId: String,
                        profileData: EditProfileData,
    ) = repository.editProfile(profileId, profileData)
}