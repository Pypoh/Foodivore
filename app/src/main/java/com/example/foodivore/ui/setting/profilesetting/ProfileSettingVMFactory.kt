package com.example.foodivore.ui.setting.profilesetting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodivore.ui.auth.domain.IAuth
import com.example.foodivore.ui.main.profile.domain.IProfile

class ProfileSettingVMFactory(
    private val useCaseProfile: IProfile,
    private val useCaseUser: IAuth
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            IProfile::class.java,
            IAuth::class.java
        ).newInstance(useCaseProfile, useCaseUser)
    }
}