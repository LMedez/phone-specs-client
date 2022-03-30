package com.luc.phonespecs.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.luc.common.NetworkStatus
import com.luc.phonespecs.R
import com.luc.phonespecs.base.BaseFragment
import com.luc.phonespecs.databinding.FragmentHomeBinding
import com.luc.phonespecs.ui.home.adapter.BestCameraPhonesAdapter
import com.luc.phonespecs.ui.home.adapter.LatestPhonesAdapter
import com.luc.presentation.viewmodel.LoginViewModel
import com.luc.presentation.viewmodel.PhonesViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


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

        phonesViewModel.isFetchingData.observe(viewLifecycleOwner) {
            if (it) {
                binding.contentContainer.visibility = View.INVISIBLE
                binding.loading.show()
            } else {
                binding.contentContainer.visibility = View.VISIBLE
                binding.loading.hide()
            }
        }
    }
}