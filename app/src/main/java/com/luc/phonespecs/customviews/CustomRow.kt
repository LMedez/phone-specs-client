package com.luc.phonespecs.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.luc.phonespecs.R
import com.luc.phonespecs.databinding.CustomRowBinding

class CustomRow @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = CustomRowBinding.inflate(LayoutInflater.from(context), this)

    init {
        binding.divider.setBackgroundColor(
            ContextCompat.getColor(
                context,
                R.color.color_primary_dark_alpha_12
            )
        )
    }

    fun setValues(key: String, value: String) {
        binding.key.text = key
        binding.value.text = value
    }
}