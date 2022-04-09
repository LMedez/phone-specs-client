package com.luc.phonespecs.ui.error

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.luc.phonespecs.base.BaseFragment
import com.luc.phonespecs.databinding.FragmentErrorBinding
import com.luc.phonespecs.utils.authErrors


class ErrorFragment : BaseFragment<FragmentErrorBinding>(FragmentErrorBinding::inflate) {

    private val args: ErrorFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.error.text = getString(authErrors(args.errorMessage))

        binding.tryAgain.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}