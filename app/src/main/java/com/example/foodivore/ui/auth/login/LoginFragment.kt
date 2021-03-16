package com.example.foodivore.ui.auth.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.foodivore.R
import com.example.foodivore.databinding.FragmentHomeBinding
import com.example.foodivore.databinding.FragmentLoginBinding
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

class LoginFragment : Fragment() {

    private lateinit var loginDataBinding: FragmentLoginBinding

    // Views
    private lateinit var emailTextInput: TextInputEditText
    private lateinit var passwordTextInput: TextInputEditText
    private lateinit var loginButton: MaterialButton
    private lateinit var toSignupButton: MaterialTextView
//
//    private lateinit var sessionManager: SessionManager
//    private lateinit var apiClient: ApiClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loginDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

//        apiClient = ApiClient()
//        sessionManager = SessionManager(requireContext())

        setupViews(loginDataBinding.root)

        // Inflate the layout for this fragment
        return loginDataBinding.root
    }

    private fun setupViews(view: View) {
        emailTextInput = view.findViewById(R.id.iet_email_login)
        passwordTextInput = view.findViewById(R.id.iet_pass_login)
        loginButton = view.findViewById(R.id.btn_login_login)
        loginButton.setOnClickListener {
            (activity as AuthActivity).apiClient.getApiService().login(
                User.LoginRequest(
                    email = emailTextInput.text.toString(),
                    password = passwordTextInput.text.toString()
                )
            ).enqueue(object : Callback<User.LoginResponse> {
                override fun onResponse(
                    call: Call<User.LoginResponse>,
                    response: Response<User.LoginResponse>
                ) {
                    val loginResponse = response.body()

                    if (loginResponse?.statusCode == 200) {
                        (activity as AuthActivity).sessionManager.saveAuthToken(loginResponse.authToken)

                    } else {
                        // Error logging in
                    }
                }

                override fun onFailure(call: Call<User.LoginResponse>, t: Throwable) {

                }

            })
        }

        toSignupButton = view.findViewById(R.id.btn_to_signup_login)
        toSignupButton.setOnClickListener {
            moveToSignUp()
        }

    }

    private fun moveToSignUp() {
        requireView().findNavController().navigate(R.id.navigation_signup)
    }
}