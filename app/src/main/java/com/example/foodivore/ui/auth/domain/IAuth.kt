package com.example.foodivore.ui.auth.domain

import android.content.Context
import com.example.foodivore.utils.viewobject.Resource

interface IAuth {
    suspend fun getAuthInstance(context: Context): Resource<String?>
}