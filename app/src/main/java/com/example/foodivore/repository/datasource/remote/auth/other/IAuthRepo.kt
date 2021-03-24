package com.example.foodivore.repository.datasource.remote.auth.other

import android.content.Context
import com.example.foodivore.repository.model.User
import com.example.foodivore.utils.viewobject.Resource

interface IAuthRepo {
    suspend fun getAuthInstance(context: Context): Resource<String?>
//    suspend fun getUserID(): Resource<FirebaseUser>
//    suspend fun getUserData(): Resource<User.UserData?>
//
//    suspend fun logout(): Resource<FirebaseAuth?>
}