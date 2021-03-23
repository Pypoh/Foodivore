package com.example.foodivore.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import java.lang.Exception

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.log(tag: String, message: String) {
    Log.d(tag, message)
}

class CustomException(message: String) : Exception(message)