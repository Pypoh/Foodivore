package com.example.foodivore.ui.auth.signup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.foodivore.R
import com.example.foodivore.databinding.FragmentLoginBinding
import com.example.foodivore.databinding.FragmentSignupBinding
import com.example.foodivore.network.ApiClient
import com.example.foodivore.network.SessionManager
import com.example.foodivore.repository.model.User
import com.example.foodivore.ui.auth.AuthActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupFragment : Fragment() {

    private lateinit var signupDataBinding: FragmentSignupBinding

    // Views
    private lateinit var emailTextInput: TextInputEditText
    private lateinit var passwordTextInput: TextInputEditText
    private lateinit var confPasswordTextInput: TextInputEditText
    private lateinit var signupButton: MaterialButton
    private lateinit var toLoginButton: MaterialTextView

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        signupDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false)

        setupViews(signupDataBinding.root)

        return signupDataBinding.root
    }

    private fun setupViews(view: View) {
        emailTextInput = view.findViewById(R.id.iet_email_signup)
        passwordTextInput = view.findViewById(R.id.iet_pass_signup)
        confPasswordTextInput = view.findViewById(R.id.iet_pass_conf_signup)
        signupButton = view.findViewById(R.id.btn_signup_signup)

        signupButton.setOnClickListener {
            Log.d("AuthDebug", "Signup...")
            (activity as AuthActivity).apiClient.getApiService().signup(
                User.SignUpRequest(
                    email = emailTextInput.text.toString(),
                    password = passwordTextInput.text.toString(),
                )
            ).enqueue(object : Callback<User.SignUpResponse> {
                override fun onResponse(
                    call: Call<User.SignUpResponse>,
                    response: Response<User.SignUpResponse>
                ) {
                    val signupResponse = response.body()
                    Log.d("AuthDebug", "Signup response: $signupResponse")
                    Log.d("AuthDebug", "Signup Url: ${response.raw().request.url}")

//                    if (signupResponse?.statusCode == 200) {
//                        (activity as AuthActivity).sessionManager.saveAuthToken(signupResponse.authToken)
//                    } else {
//                        // Error logging in
//                    }
                }

                override fun onFailure(call: Call<User.SignUpResponse>, t: Throwable) {
                    Log.d("AuthDebug", "Signup failure: ${t.message}")
                }

            })
        }

        toLoginButton = view.findViewById(R.id.btn_to_login_signup)
        toLoginButton.setOnClickListener {
            moveToLogin()
        }

    }

    private fun moveToLogin() {
        requireView().findNavController().navigate(R.id.navigation_login)
    }
}