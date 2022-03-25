package com.luc.phonespecs.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.doOnLayout
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.platform.MaterialFadeThrough
import com.luc.phonespecs.base.BaseFragment
import com.luc.phonespecs.databinding.FragmentSplashScreenBinding
import com.luc.presentation.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


@SuppressLint("CustomSplashScreen")
class SplashScreenFragment :
    BaseFragment<FragmentSplashScreenBinding>(FragmentSplashScreenBinding::inflate) {
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exitTransition = MaterialFadeThrough().setDuration(1000).addTarget(binding.root)

        binding.root.postDelayed({
            loginViewModel.getUserLogged().observe(viewLifecycleOwner) {
                if (it != null) {
                    findNavController().navigate(
                        SplashScreenFragmentDirections.actionSplashScreenFragmentToHomeFragment(
                            it
                        )
                    )
                } else {
                    findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToSignInLoginFragment())
                }

            }
        },1000)

    }
}