package com.example.foodivore.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.foodivore.MainActivity
import com.example.foodivore.R
import com.example.foodivore.databinding.FragmentLoginBinding
import com.example.foodivore.repository.datasource.remote.auth.login.LoginRepoImpl
import com.example.foodivore.repository.datasource.remote.profile.ProfileRepoImpl
import com.example.foodivore.repository.model.User
import com.example.foodivore.ui.auth.AuthActivity
import com.example.foodivore.ui.auth.InBoardingActivity
import com.example.foodivore.ui.auth.login.domain.LoginImpl
import com.example.foodivore.ui.main.profile.domain.ProfileImpl
import com.example.foodivore.ui.pretest.PreTestActivity
import com.example.foodivore.utils.toast
import com.example.foodivore.utils.viewobject.Resource
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {

    private lateinit var loginDataBinding: FragmentLoginBinding

    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProvider(
            this,
            LoginVMFactory(LoginImpl(LoginRepoImpl()), ProfileImpl(ProfileRepoImpl()))
        ).get(LoginViewModel::class.java)
    }

    // Views
    private lateinit var emailTextInput: TextInputEditText
    private lateinit var passwordTextInput: TextInputEditText
    private lateinit var loginButton: MaterialButton
    private lateinit var toSignupButton: MaterialTextView
    private lateinit var alertDialog: AlertDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loginDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        loginDataBinding.loginViewModel = loginViewModel

        setupViews(loginDataBinding.root)

        initProgressDialog()

        setupButtonListener()

        return loginDataBinding.root
    }

    private fun setupButtonListener() {
        loginButton.setOnClickListener {
            loginViewModel.loginWithEmailAndPassword().removeObservers(viewLifecycleOwner)
            loginViewModel.loginWithEmailAndPassword().observe(viewLifecycleOwner, { task ->
                when (task) {
                    is Resource.Loading -> {
                        if (!alertDialog.isShowing) alertDialog.show()
                    }

                    is Resource.Success -> {
                        if (alertDialog.isShowing) alertDialog.dismiss()
                        if (task.data!!.accessToken.isNotEmpty()) {
                            (activity as AuthActivity).sessionManager.saveAuthToken(task.data.accessToken)

//                            profileCheck(task.data.accessToken)
                            if (task.data.calorieNeeds == 0) {
                                Log.d(
                                    "LoginFragment",
                                    "User ${task.data.id} has not been initialized"
                                )
                                intentToPreTest()
                            } else {
                                intentToMain()
                            }

                        }
                    }

                    is Resource.Failure -> {
                        if (alertDialog.isShowing) alertDialog.dismiss()
                        requireContext().toast(task.throwable.message.toString())
                    }

                    else -> {
                        // do nothing
                        requireContext().toast(task.toString())
                    }
                }
            })


        }

        toSignupButton.setOnClickListener {
            moveToSignUp()
        }
    }

    private fun profileCheck(jwtToken: String) {
        loginViewModel.profileChecker(jwtToken).removeObservers(viewLifecycleOwner)
        loginViewModel.profileChecker(jwtToken).observe(viewLifecycleOwner, { task ->
            when (task) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Log.d("LoginFragment", "user calorie : ${task.data}")
                    if (task.data!!.calorieNeeds == 0) {
                        intentToPreTest()
                    } else {
                        intentToMain()
                    }
                }

                is Resource.Failure -> {

                }

                else -> {

                }
            }
        })
    }

    private fun setupViews(view: View) {
        emailTextInput = view.findViewById(R.id.iet_email_login)
        passwordTextInput = view.findViewById(R.id.iet_pass_login)
        loginButton = view.findViewById(R.id.btn_login_login)
        toSignupButton = view.findViewById(R.id.btn_to_signup_login)
    }

    private fun moveToSignUp() {
        requireView().findNavController().navigate(R.id.navigation_signup)
    }

    private fun intentToMain() {
        startActivity(Intent(requireContext(), MainActivity::class.java))
        requireActivity().finish()
    }

    private fun intentToPreTest() {
        startActivity(Intent(requireContext(), PreTestActivity::class.java))
        requireActivity().finish()

    }

    private fun initProgressDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.progress_dialog, null)
        dialogBuilder.setView(dialogView)
        dialogBuilder.setCancelable(false)
        alertDialog = dialogBuilder.create()
    }
}