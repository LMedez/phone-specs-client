package com.luc.phonespecs.ui.login

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.luc.common.NetworkStatus
import com.luc.phonespecs.R
import com.luc.phonespecs.base.BaseFragment
import com.luc.phonespecs.databinding.FragmentSignInLoginBinding
import com.luc.presentation.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Exception


class SignInLoginFragment :
    BaseFragment<FragmentSignInLoginBinding>(FragmentSignInLoginBinding::inflate) {

    private val loginViewModel: LoginViewModel by viewModel()
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUp.setOnClickListener {
            findNavController().navigate(SignInLoginFragmentDirections.actionSignInLoginFragmentToLoginFragment())
        }

        binding.login.setOnClickListener {
            if (binding.emailInput.editTextHasError() or binding.passwordInput.editTextHasError()) {

            } else {
                loginViewModel.signInWithEmailAndPassword(
                    binding.emailInput.getEditText().text.toString(),
                    binding.passwordInput.getEditText().text.toString()
                ).observe(viewLifecycleOwner) {
                    when (it) {
                        is NetworkStatus.Loading -> {
                            binding.loading.show()
                        }
                        is NetworkStatus.Error -> {
                            binding.loading.hide()
                            binding.invalidUser.text = getErrorMessage(it.exception as FirebaseAuthException)
                        }
                        is NetworkStatus.Success -> {
                            binding.loading.hide()
                            findNavController().navigate(
                                SignInLoginFragmentDirections.actionSignInLoginFragmentToHomeFragment(
                                    it.data
                                )
                            )
                        }
                    }
                }
            }

        }

        binding.googleSignIn.setOnClickListener {
            getContent.launch(googleSignInClient.signInIntent)
        }
    }

    private fun getErrorMessage(exception: FirebaseAuthException): String {
        return when (exception) {
            is FirebaseAuthInvalidCredentialsException -> getString(R.string.user_invalid_password)
            is FirebaseAuthInvalidUserException -> getString(R.string.user_does_not_exists)
            else -> exception.message ?: ""
        }
    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK)
                onActivityResult(CODE_REQUEST, result)
        }

    private fun onActivityResult(requestCode: Int, result: ActivityResult) {
        val intent = result.data
        when (requestCode) {
            CODE_REQUEST -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)
                    if (account != null) {
                        loginViewModel.signInWithGoogle(account.idToken!!)
                            .observe(viewLifecycleOwner) {
                                when (it) {
                                    NetworkStatus.Loading -> {
                                        binding.loading.show()
                                    }
                                    is NetworkStatus.Success -> {
                                        binding.loading.hide()
                                        findNavController().navigate(
                                            SignInLoginFragmentDirections.actionSignInLoginFragmentToHomeFragment(
                                                it.data
                                            )
                                        )
                                    }
                                    is NetworkStatus.Error -> {
                                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                                            .show()
                                        binding.loading.hide()
                                    }
                                }
                            }
                    }
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                }
            }
        }
    }
}

const val CODE_REQUEST = 12