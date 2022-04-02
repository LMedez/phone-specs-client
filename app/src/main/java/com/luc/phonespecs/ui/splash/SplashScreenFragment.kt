package com.luc.phonespecs.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.doOnLayout
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.platform.MaterialFadeThrough
import com.luc.phonespecs.R
import com.luc.phonespecs.base.BaseFragment
import com.luc.phonespecs.databinding.FragmentSplashScreenBinding
import com.luc.presentation.viewmodel.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


@SuppressLint("CustomSplashScreen")
class SplashScreenFragment :
    BaseFragment<FragmentSplashScreenBinding>(FragmentSplashScreenBinding::inflate) {
    private val splashScreenViewModel: SplashScreenViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exitTransition = MaterialFadeThrough().setDuration(1000).addTarget(binding.root)

        splashScreenViewModel.navigateToHome.observe(viewLifecycleOwner) {
            findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToHomeFragment(it))
        }

        splashScreenViewModel.navigateToLogin.observe(viewLifecycleOwner) {
            findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToSignInLoginFragment())
        }
    }
}