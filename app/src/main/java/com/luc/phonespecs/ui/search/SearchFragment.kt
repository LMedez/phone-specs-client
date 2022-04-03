package com.luc.phonespecs.ui.search

import android.graphics.Color
import android.os.Bundle
import android.transition.PathMotion
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.luc.phonespecs.R
import com.luc.phonespecs.base.BaseFragment
import com.luc.phonespecs.databinding.FragmentSearchBinding
import com.luc.phonespecs.utils.themeColor
import com.luc.presentation.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {
    private val searchViewModel: SearchViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            scrimColor = Color.TRANSPARENT
            pathMotion = MaterialArcMotion()
            duration = resources.getInteger(R.integer.reply_motion_duration_medium).toLong()
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        searchViewModel.isSearching.observe(viewLifecycleOwner) {

        }

        searchViewModel.showErrorMessage.observe(viewLifecycleOwner) {

        }




        searchViewModel.searchResult.observe(viewLifecycleOwner) {

        }
    }

}