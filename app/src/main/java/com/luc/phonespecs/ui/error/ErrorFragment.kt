package com.luc.phonespecs.ui.error

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.luc.phonespecs.base.BaseFragment
import com.luc.phonespecs.databinding.FragmentErrorBinding
import com.luc.phonespecs.ui.home.HomeFragmentArgs

private const val ERROR_MESSAGE = "param1"

class ErrorFragment : BaseFragment<FragmentErrorBinding>(FragmentErrorBinding::inflate) {

    private var errorMessage: String? = null
    private val args: ErrorFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            errorMessage = it.getString(ERROR_MESSAGE)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.error.text = args.errorMessage

        binding.tryAgain.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    companion object {
        @JvmStatic fun newInstance(errorMessage: String) =
                ErrorFragment().apply {
                    arguments = Bundle().apply {
                        putString(ERROR_MESSAGE, errorMessage)
                    }
                }
    }
}