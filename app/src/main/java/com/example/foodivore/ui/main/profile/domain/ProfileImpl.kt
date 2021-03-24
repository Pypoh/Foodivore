package com.example.foodivore.ui.main.profile.domain

import com.example.foodivore.repository.datasource.remote.profile.IProfileRepo

class ProfileImpl(private val profileRepository: IProfileRepo) : IProfile  {
}