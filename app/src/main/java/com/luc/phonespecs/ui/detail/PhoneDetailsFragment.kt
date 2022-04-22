package com.luc.phonespecs.ui.detail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.luc.common.model.phonespecs.PhoneDetail
import com.luc.phonespecs.base.BaseFragment
import com.luc.phonespecs.databinding.FragmentPhoneDetailBinding

class PhoneDetailsFragment :
    BaseFragment<FragmentPhoneDetailBinding>(FragmentPhoneDetailBinding::inflate) {

    private val args: PhoneDetailsFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.phoneDetails = args.phoneDetail
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X,/* forward= */ true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)

        setUpDetails(args.phoneDetail)
    }

    private fun setUpDetails(phoneDetail: PhoneDetail) {
        with(binding) {
            phoneDetail.hardware?.battery?.let {
                battery.addRow("Charging power", it.chargingPower ?: "NaN")
                battery.addRow("Type", it.type ?: "NaN")
                battery.addRow("Life", it.life ?: "NaN")
            }

            phoneDetail.hardware?.processor?.let {
                processor.addRow("Chipset", it.chipset ?: "NaN")
                processor.addRow("GPU", it.GPU ?: "NaN")
                processor.addRow("CPU", it.CPU ?: "NaN")
            }

            phoneDetail.hardware?.memory?.let {
                memory.addRow("Card Slot", it.cardSlot ?: "NaN")
                memory.addRow("RAM", it.ram?.joinToString { it } ?: "NaN")
                memory.addRow("Internal Storage", it.internal?.joinToString { it } ?: "NaN")
            }

            phoneDetail.software?.let {
                software.addRow("OS", it.os ?: "NaN")
                software.addRow("OS Version", it.osVersion ?: "NaN")
            }

            phoneDetail.backCamera?.let {
                backCamera.addRow("Video", it.video?.joinToString { it } ?: "NaN")
                backCamera.addRow("MP", it.mp?.joinToString { it } ?: "NaN")
                backCamera.addRow("Features", it.features?.joinToString { it } ?: "NaN")
            }

            phoneDetail.frontCamera?.let {
                frontCamera.addRow("Video", it.video?.joinToString { it } ?: "NaN")
                frontCamera.addRow("MP", it.mp?.joinToString { it } ?: "NaN")
                frontCamera.addRow("Features", it.features?.joinToString { it } ?: "NaN")
            }

            phoneDetail.wireless?.let {
                wireless.addRow("Wifi", it.wifi?.joinToString { it } ?: "NaN")
                wireless.addRow("USB", it.usb?.joinToString { it } ?: "NaN")
                wireless.addRow("Bluetooth", it.bluetooth?.joinToString { it } ?: "NaN")
            }

            phoneDetail.display?.let {
                display.addRow("Type", it.type ?: "NaN")
                display.addRow("Resolution", it.resolution ?: "NaN")
                display.addRow("Inch", it.inch ?: "NaN")
                display.addRow("Hz", it.hz ?: "NaN")
                display.addRow("Aspect Ratio", it.aspectRatio ?: "NaN")
                display.addRow("ppi", it.ppi.toString())
            }
        }
    }
}