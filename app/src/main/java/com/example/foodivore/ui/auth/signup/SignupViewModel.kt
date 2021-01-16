package com.example.foodivore.ui.auth.signup

import androidx.lifecycle.MutableLiveData

class SignupViewModel {

    var email: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()
    var confPassword: MutableLiveData<String> = MutableLiveData()



}