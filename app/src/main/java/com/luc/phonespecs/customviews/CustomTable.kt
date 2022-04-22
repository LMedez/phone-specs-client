package com.luc.phonespecs.customviews

import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.luc.phonespecs.R
import com.luc.phonespecs.databinding.CustomTableBinding

class CustomTable @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    private val binding = CustomTableBinding.inflate(LayoutInflater.from(context), this)

    init {
        context.withStyledAttributes(attrs, R.styleable.CustomTable) {
            val text = getString(R.styleable.CustomTable_title)
            val icon = getDrawable(R.styleable.CustomTable_icon)
            binding.image.background = icon
            binding.title.text = text

            val sheetBackground = MaterialShapeDrawable(
                ShapeAppearanceModel.builder(
                    context,
                    R.style.ShapeAppearance_App_MediumComponent,
                    0
                ).build()
            ).apply {
                fillColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white))
            }
            background = sheetBackground

        }
    }

    fun addRow(key: String, value: String) {
        val row = CustomRow(context)
        row.setValues(key, value)
        binding.rowsContainer.addView(row)
    }
}