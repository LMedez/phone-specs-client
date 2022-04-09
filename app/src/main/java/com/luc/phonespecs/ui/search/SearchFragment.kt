package com.luc.phonespecs.ui.search

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.transition.*
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.luc.phonespecs.R
import com.luc.phonespecs.base.BaseFragment
import com.luc.phonespecs.databinding.FragmentSearchBinding
import com.luc.phonespecs.ui.home.adapter.LatestPhonesAdapter
import com.luc.phonespecs.ui.search.adapter.SearchResultAdapter
import com.luc.phonespecs.ui.search.adapter.SimpleItemAdapter
import com.luc.presentation.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val searchViewModel: SearchViewModel by viewModel()
    private val itemsAdapter: SimpleItemAdapter by lazy { SimpleItemAdapter() }
    private val searchResultAdapter: SearchResultAdapter by lazy { SearchResultAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            scrimColor = Color.TRANSPARENT
            pathMotion = MaterialArcMotion()
            duration = resources.getInteger(R.integer.reply_motion_duration_medium).toLong()
            addListener(object : Transition.TransitionListener{
                override fun onTransitionStart(p0: Transition?) {}

                override fun onTransitionEnd(p0: Transition?) {
                    TransitionManager.beginDelayedTransition(
                        binding.recentContainer,
                        MaterialSharedAxis(MaterialSharedAxis.Y, false).apply {
                            duration = resources.getInteger(R.integer.reply_motion_duration_small).toLong()
                        })
                    binding.recentContainer.visibility = View.VISIBLE
                }

                override fun onTransitionCancel(p0: Transition?) {}

                override fun onTransitionPause(p0: Transition?) {}

                override fun onTransitionResume(p0: Transition?) {}
            })
        }

        binding.resultRecycler.adapter = searchResultAdapter

        val list = listOf("item1", "item2", "item3", "item4")
        itemsAdapter.submitList(list)
        binding.itemsRecycler.adapter = itemsAdapter

        binding.search.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
            }
            false
        }
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        searchViewModel.isSearching.observe(viewLifecycleOwner) {
            if (it) {
                TransitionManager.beginDelayedTransition(
                    binding.recentContainer,
                    MaterialSharedAxis(MaterialSharedAxis.Y, true).apply {
                        duration = resources.getInteger(R.integer.reply_motion_duration_small)
                            .toLong()
                    })
                binding.recentContainer.visibility = View.INVISIBLE
                binding.searchingProgress.show()
            } else {
                binding.searchingProgress.hide()
            }
        }

        searchViewModel.showErrorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }

        searchViewModel.searchResult.observe(viewLifecycleOwner) {
            searchResultAdapter.submitList(it)
        }
    }

    private fun performSearch() {
        binding.search.clearFocus()
        val input: InputMethodManager? = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        input?.hideSoftInputFromWindow(binding.search.windowToken, 0)

        searchViewModel.search(binding.search.text.toString())
    }

}