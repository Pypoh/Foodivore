package com.example.foodivore.ui.auth.domain

import android.content.Context
import com.example.foodivore.repository.datasource.auth.other.IAuthRepo
import com.example.foodivore.utils.viewobject.Resource

class AuthImpl(private val authRepository: IAuthRepo) : IAuth {
    override suspend fun getAuthInstance(context: Context): Resource<String?> =
        authRepository.getAuthInstance(context)
}