package com.example.foodivore.repository.datasource.remote.auth.other

import android.content.Context
import com.example.foodivore.network.ApiClient
import com.example.foodivore.network.SessionManager
import com.example.foodivore.utils.viewobject.Resource
import java.lang.Exception

class AuthRepoImpl : IAuthRepo {
    lateinit var sessionManager: SessionManager
    lateinit var apiClient: ApiClient

    override suspend fun getAuthInstance(context: Context): Resource<String?> {
        sessionManager = SessionManager(context)
        return try {
            Resource.Success(sessionManager.fetchAuthToken())
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }
}