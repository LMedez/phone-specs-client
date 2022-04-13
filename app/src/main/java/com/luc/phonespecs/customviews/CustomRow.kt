package com.luc.phonespecs.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.luc.phonespecs.R
import com.luc.phonespecs.databinding.CustomRowBinding

class CustomRow @JvmOverloads constructor (context: Context,
                  attrs: AttributeSet? = null,
                  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = CustomRowBinding.inflate(LayoutInflater.from(context), this)

    init {
        context.withStyledAttributes(attrs, R.styleable.CustomRow) {
            val key1 = getString(R.styleable.CustomRow_key1)
            val key2 = getString(R.styleable.CustomRow_key2)
            val value1 = getString(R.styleable.CustomRow_value1)
            val value2 = getString(R.styleable.CustomRow_value2)
            binding.key1.text = key1
            binding.key2.text = key2
            binding.value1.text = value1
            binding.value2.text = value2

            binding.divider.setBackgroundColor(ContextCompat.getColor(context,R.color.color_primary_dark_alpha_12))


        }
    }
}