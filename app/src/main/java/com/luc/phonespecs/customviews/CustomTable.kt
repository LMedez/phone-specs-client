package com.luc.phonespecs.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.content.withStyledAttributes
import com.luc.common.model.phonespecs.PhoneDetail
import com.luc.phonespecs.R
import com.luc.phonespecs.customviews.CustomTable.SpecType.*
import com.luc.phonespecs.databinding.CustomTableBinding

class CustomTable @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding = CustomTableBinding.inflate(LayoutInflater.from(context), this)

    init {
        context.withStyledAttributes(attrs, R.styleable.CustomTable) {
            val text = getString(R.styleable.CustomTable_title)
            binding.title.text = text
        }
    }

    /**
    * This should be reworked in futures commits
    *
    * */
    fun addSpecs(specType: SpecType, phoneDetail: PhoneDetail) {
        when (specType) {
            SOFTWARE ->
                phoneDetail.software?.let {
                    binding.title.text = "Software"
                    addRow(it.os ?: "", it.osVersion ?: "", "OS", "OS Version")
                }
            BATTERY -> phoneDetail.hardware?.battery?.let {
                binding.title.text = "Battery"
                addRow(it.life ?: "", it.type ?: "", "Life", "Type")
                addRow(it.chargingPower ?: "", key1 = "Power")
            }
            MEMORY -> phoneDetail.hardware?.memory?.let { memory ->
                binding.title.text = "Memory"
                addRow(memory.cardSlot ?: "", memory.internal?.joinToString { "$it " } ?: "","Card Slot", "Internal")
                addRow(memory.ram?.joinToString { "$it " } ?: "", key1 = "RAM")
            }
            PROCESSOR -> phoneDetail.hardware?.processor?.let { processor ->
                binding.title.text = "Processor"
                addRow(processor.CPU ?: "", processor.GPU ?: "", "CPU", "GPU")
                addRow(processor.chipset ?: "", key1 = "Chipset")
            }
            WIRELESS -> phoneDetail.wireless?.let { wireless ->
                binding.title.text = "Wireless"
                addRow(wireless.bluetooth?.joinToString { "$it " } ?: "",
                    wireless.usb?.joinToString { "$it " } ?: "", "Bluetooth", "USB")
                addRow(wireless.wifi?.joinToString { "$it " } ?: "", key1 = "WiFi")
            }
            DISPLAY -> phoneDetail.display?.let {
                binding.title.text = "Display"
                addRow(it.aspectRatio ?: "", it.hz ?: "", "Aspect Ratio", "HZ")
                addRow(it.inch ?: "", it.resolution ?: "", "Inch", "Resolution")
                addRow(it.type ?: "", it.ppi.toString(), "Type", "PPI")
            }
            BACK_CAMERA -> phoneDetail.backCamera?.let { camera ->
                binding.title.text = "Back Camera"
                addRow(
                    camera.features?.joinToString { "$it " } ?: "",
                    camera.mp?.joinToString { "$it " } ?: "", "Features", "MP"
                )
                addRow(camera.video?.joinToString { "$it " } ?: "", key1 = "Video")
            }
            AUDIO -> phoneDetail.audio?.let {
                binding.title.text = "Audio"
                addRow(it.output, if (it.hasOutput == true) "Yes" else "No", "Output", "Has output" )
            }
            FRONT_CAMERA -> phoneDetail.frontCamera?.let { camera ->
                binding.title.text = "Front Camera"
                addRow(
                    camera.features?.joinToString { "$it " } ?: "",
                    camera.mp?.joinToString { "$it " } ?: "","Features", "MP"
                )
                addRow(camera.video?.joinToString { "$it " } ?: "", key1 = "Video")
            }
        }

    }

    private fun addRow(value1: String, value2: String = "", key1: String, key2: String = "") {
        val row = CustomRow(context)
        row.setValues(value1, value2, key1, key2)
        addView(row)
    }

    enum class SpecType {
        SOFTWARE,
        MEMORY,
        PROCESSOR,
        WIRELESS,
        DISPLAY,
        BACK_CAMERA,
        FRONT_CAMERA,
        BATTERY,
        AUDIO
    }
}