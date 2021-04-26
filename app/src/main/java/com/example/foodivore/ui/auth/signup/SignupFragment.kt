package com.example.foodivore.ui.auth.signup

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.foodivore.R
import com.example.foodivore.databinding.FragmentSignupBinding
import com.example.foodivore.network.ApiClient
import com.example.foodivore.network.SessionManager
import com.example.foodivore.repository.datasource.remote.auth.signup.SignupRepoImpl
import com.example.foodivore.ui.auth.AuthActivity
import com.example.foodivore.ui.auth.InBoardingActivity
import com.example.foodivore.ui.auth.signup.domain.SignUpImpl
import com.example.foodivore.ui.pretest.PreTestActivity
import com.example.foodivore.utils.toast
import com.example.foodivore.utils.viewobject.Resource
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView

class SignupFragment : Fragment() {

    private lateinit var signupDataBinding: FragmentSignupBinding

    private val signUpViewModel: SignupViewModel by lazy {
        ViewModelProvider(
            this,
            SignupVMFactory(SignUpImpl(SignupRepoImpl()))
        ).get(SignupViewModel::class.java)
    }

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
    ): View {

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
            signUpViewModel.signUpWithEmailAndPassword().removeObservers(viewLifecycleOwner)
            signUpViewModel.signUpWithEmailAndPassword().observe(viewLifecycleOwner, { task ->
                when (task) {
                    is Resource.Loading -> {
                    }

                    is Resource.Success -> {
                        (activity as AuthActivity).sessionManager.saveAuthToken(task.data!!.accessToken)
                        intentToPreTest()
                    }

                    is Resource.Failure -> {
                    }

                    else -> {
                        // do nothing
                        requireContext().toast(task.toString())
                    }
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

    private fun intentToPreTest() {
        startActivity(Intent(requireContext(), PreTestActivity::class.java))
        requireActivity().finish()

    }
}