package com.luc.phonespecs.ui.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.luc.common.NetworkStatus
import com.luc.phonespecs.R
import com.luc.phonespecs.base.BaseFragment
import com.luc.phonespecs.databinding.FragmentRegisterLoginBinding
import com.luc.presentation.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlin.math.log

class RegisterLoginFragment :
    BaseFragment<FragmentRegisterLoginBinding>(FragmentRegisterLoginBinding::inflate) {

    private val loginViewModel: LoginViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            signIn.setOnClickListener {
                findNavController().popBackStack()
            }
            loginViewModel.navigateToHome.observe(viewLifecycleOwner) {
                findNavController().navigate(
                    RegisterLoginFragmentDirections.actionNavContainerToHomeFragment(
                        it
                    )
                )
            }

            loginViewModel.showError.observe(viewLifecycleOwner) {
                binding.loading.hide()
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }

            loginViewModel.isLoading.observe(viewLifecycleOwner) {
                if (it) loading.show()
                else loading.hide()
            }
            createAccount.setOnClickListener {
                if (emailInput.editTextHasError() or passwordInput.editTextHasError()) {

                } else {
                    loginViewModel.signUpWithEmailAndPassword(
                        fullNameInput.getEditText().text.toString(),
                        emailInput.getEditText().text.toString(),
                        passwordInput.getEditText().text.toString()
                    )
                }
            }
        }
    }
}