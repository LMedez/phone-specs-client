package com.luc.phonespecs.ui.login

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.luc.common.NetworkStatus
import com.luc.phonespecs.base.BaseFragment
import com.luc.phonespecs.databinding.FragmentRegisterLoginBinding
import com.luc.presentation.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
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

            createAccount.setOnClickListener {
                if (emailInput.editTextHasError() or passwordInput.editTextHasError()) {

                } else {
                    loginViewModel.signUpWithEmailAndPassword(
                        fullNameInput.getEditText().text.toString(),
                        emailInput.getEditText().text.toString(),
                        passwordInput.getEditText().text.toString()
                    ).observe(viewLifecycleOwner) {
                        when (it) {
                            is NetworkStatus.Loading -> loading.show()
                            is NetworkStatus.Error -> {
                                invalidUser.text = it.message
                                loading.hide()
                            }
                            is NetworkStatus.Success -> {
                                loading.hide()
                                findNavController().navigate(
                                    RegisterLoginFragmentDirections.actionNavContainerToHomeFragment(
                                        it.data
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}