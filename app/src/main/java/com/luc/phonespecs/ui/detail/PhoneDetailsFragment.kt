package com.luc.phonespecs.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.luc.phonespecs.R
import com.luc.phonespecs.base.BaseFragment
import com.luc.phonespecs.databinding.FragmentPhoneDetailBinding

class PhoneDetailsFragment : BaseFragment<FragmentPhoneDetailBinding>(FragmentPhoneDetailBinding::inflate) {

    private val args: PhoneDetailsFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.phoneDetails = args.phoneDetail
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X ,/* forward= */ true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
    }
}