package com.luc.phonespecs.ui.search

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.transition.Slide
import com.google.android.material.chip.Chip
import com.google.android.material.transition.MaterialContainerTransform
import com.luc.phonespecs.R
import com.luc.phonespecs.base.BaseFragment
import com.luc.phonespecs.databinding.FragmentSelectionSearchBinding
import com.luc.phonespecs.utils.getDrawableOrNull
import com.luc.phonespecs.utils.themeColor

class SelectionSearchFragment :
    BaseFragment<FragmentSelectionSearchBinding>(FragmentSelectionSearchBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.floating.setOnClickListener {
            findNavController().navigate(SelectionSearchFragmentDirections.actionSelectionSearchFragmentToSearchFragment())
        }

        binding.run {
            category.setOnClick { text ->
                chipGroup.addView(createChip(text))
            }
            price.setOnClick { }
            brand.setOnClick { }
        }


        binding.run {
            // Set transitions here so we are able to access Fragment's binding views.
            enterTransition = MaterialContainerTransform().apply {
                // Manually add the Views to be shared since this is not a standard Fragment to
                // Fragment shared element transition.
                startView = requireActivity().findViewById(R.id.fab)
                endView = contentEndView
                duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
                scrimColor = Color.TRANSPARENT
                containerColor = requireContext().themeColor(R.attr.colorSurface)
                startContainerColor = requireContext().themeColor(R.attr.colorSecondary)
                endContainerColor = requireContext().themeColor(R.attr.colorSurface)
            }

            returnTransition = Slide().apply {
                duration = resources.getInteger(R.integer.reply_motion_duration_medium).toLong()
                addTarget(R.id.contentEndView)
            }
        }
    }

    private fun createChip(text: String): Chip {
        val chip = Chip(requireContext())
        chip.text = text
        chip.closeIcon = requireContext().getDrawableOrNull(R.drawable.ic_close)
        chip.isCheckable = false
        chip.setOnCloseIconClickListener{
            binding.chipGroup.removeView(chip)
        }
        return chip
    }
}