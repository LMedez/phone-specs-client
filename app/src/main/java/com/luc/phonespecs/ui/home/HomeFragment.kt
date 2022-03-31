package com.luc.phonespecs.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayout
import com.luc.phonespecs.base.BaseFragment
import com.luc.phonespecs.databinding.FragmentHomeBinding
import com.luc.phonespecs.ui.home.adapter.BestCameraPhonesAdapter
import com.luc.phonespecs.ui.home.adapter.LatestPhonesAdapter
import com.luc.presentation.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val args: HomeFragmentArgs by navArgs()
    private val homeViewModel: HomeViewModel by viewModel()

    private val latestPhonesAdapter: LatestPhonesAdapter by lazy { LatestPhonesAdapter() }
    private val bestCameraPhonesAdapter: BestCameraPhonesAdapter by lazy { BestCameraPhonesAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.fetchData()

        binding.userProfile = args.userProfile
        binding.latestRv.adapter = latestPhonesAdapter
        binding.cameraRv.adapter = bestCameraPhonesAdapter

        homeViewModel.latestPhones.observe(viewLifecycleOwner) {
            latestPhonesAdapter.submitList(it)
        }

        homeViewModel.bestCameraPhones.observe(viewLifecycleOwner) {
            bestCameraPhonesAdapter.submitList(it)
        }

        homeViewModel.isFetchingPhones.observe(viewLifecycleOwner) {
            if (it) {
                binding.contentContainer.visibility = View.INVISIBLE
                binding.loading.show()
            } else {
                binding.contentContainer.visibility = View.VISIBLE
                binding.loading.hide()
            }
        }

        homeViewModel.showErrorFragment.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { message ->
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToErrorFragment(
                        message
                    )
                )
            }
        }
        
        homeViewModel.showErrorOnChangeBrand.observe(viewLifecycleOwner) {
            Toast.makeText(
                requireContext(),
                "oops! something is wrong, try again later!",
                Toast.LENGTH_SHORT
            ).show()
        }

        homeViewModel.isFetchingPhonesBestCamera.observe(viewLifecycleOwner) {
            if (it) {
                binding.loadingbestCamera.show()
                binding.cameraRv.animate().alpha(0.2f).duration = 300
            } else {
                binding.loadingbestCamera.hide()
                binding.cameraRv.animate().alpha(1f)
            }
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.text.toString() == "All") {
                    homeViewModel.setBestCameraBrand(null)
                } else homeViewModel.setBestCameraBrand(tab?.text.toString())

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })
    }

}