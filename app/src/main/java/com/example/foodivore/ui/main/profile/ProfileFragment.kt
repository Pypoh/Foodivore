package com.example.foodivore.ui.main.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.foodivore.R
import com.example.foodivore.databinding.FragmentProfileBinding
import com.example.foodivore.repository.datasource.remote.auth.other.AuthRepoImpl
import com.example.foodivore.repository.datasource.remote.profile.ProfileRepoImpl
import com.example.foodivore.ui.auth.domain.AuthImpl
import com.example.foodivore.ui.main.profile.domain.ProfileImpl

class ProfileFragment : Fragment() {

    private lateinit var profileDataBinding: FragmentProfileBinding

    private val profileViewModel: ProfileViewModel by lazy {
        ViewModelProvider(
            this,
            ProfileVMFactory(
                ProfileImpl(ProfileRepoImpl()),
                AuthImpl(AuthRepoImpl()),
            )
        ).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        profileDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)


        return profileDataBinding.root
    }


}