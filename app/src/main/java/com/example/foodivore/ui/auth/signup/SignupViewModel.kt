package com.example.foodivore.ui.auth.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.foodivore.repository.model.User
import com.example.foodivore.ui.auth.signup.domain.ISignup
import com.example.foodivore.utils.viewobject.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class SignupViewModel(private val useCaseSignUp: ISignup) : ViewModel() {

    var email: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()
    var confPassword: MutableLiveData<String> = MutableLiveData()
    lateinit var result: LiveData<Resource<User.SignUpResponse?>>

    fun signUpWithEmailAndPassword() {
        Log.d("SignupViewModel", "${email.value!!} | ${password.value!!} singup...")

        result =  liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val signUpAuthResult = useCaseSignUp.signupWithEmailAndPassword(
                    email = email.value!!,
                    password = password.value!!
                )
                emit(signUpAuthResult)
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }


}