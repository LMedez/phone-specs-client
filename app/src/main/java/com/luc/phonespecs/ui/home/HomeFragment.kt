package com.luc.phonespecs.ui.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayout
import com.luc.phonespecs.base.BaseFragment
import com.luc.phonespecs.databinding.FragmentHomeBinding
import com.luc.phonespecs.ui.home.adapter.BestCameraPhonesAdapter
import com.luc.phonespecs.ui.home.adapter.LatestPhonesAdapter
import com.luc.presentation.viewmodel.PhonesViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val args: HomeFragmentArgs by navArgs()
    private val phonesViewModel: PhonesViewModel by sharedViewModel()

    private val latestPhonesAdapter: LatestPhonesAdapter by lazy { LatestPhonesAdapter() }
    private val bestCameraPhonesAdapter: BestCameraPhonesAdapter by lazy { BestCameraPhonesAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.userProfile = args.userProfile
        binding.latestRv.adapter = latestPhonesAdapter
        binding.cameraRv.adapter = bestCameraPhonesAdapter

        phonesViewModel.latestPhones.observe(viewLifecycleOwner) {
            latestPhonesAdapter.submitList(it)
        }

        phonesViewModel.bestCameraPhones.observe(viewLifecycleOwner) {
            bestCameraPhonesAdapter.submitList(it)
        }

        phonesViewModel.isFetchingPhones.observe(viewLifecycleOwner) {
            if (it) {
                binding.contentContainer.visibility = View.INVISIBLE
                binding.loading.show()
            } else {
                binding.contentContainer.visibility = View.VISIBLE
                binding.loading.hide()
            }
        }

        phonesViewModel.error.observe(viewLifecycleOwner) {

        }

        phonesViewModel.isFetchingPhonesBestCamera.observe(viewLifecycleOwner) {
            if (it) {
                binding.loadingbestCamera.show()
                binding.cameraRv.animate().alpha(0.2f).setDuration(300)
            } else {
                binding.loadingbestCamera.hide()
                binding.cameraRv.animate().alpha(1f)
            }

        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                phonesViewModel.setBestCameraBrand(tab?.text.toString())
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })
    }
}