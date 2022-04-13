package com.luc.phonespecs.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.content.withStyledAttributes
import com.luc.phonespecs.R
import com.luc.phonespecs.databinding.CustomTableBinding

class CustomTable @JvmOverloads constructor(context: Context,
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
}