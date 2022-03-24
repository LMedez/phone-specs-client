package com.luc.phonespecs.customviews

import android.content.Context
import android.content.res.TypedArray
import android.text.InputType.TYPE_CLASS_TEXT
import android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.textclassifier.TextClassifier.TYPE_EMAIL
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import com.luc.phonespecs.R
import com.luc.phonespecs.databinding.CustomEditTextBinding
import android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

class CustomEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = CustomEditTextBinding.inflate(LayoutInflater.from(context), this)

    init {
        context.withStyledAttributes(attrs, R.styleable.CustomEditText) {
            val typeEditText = getString(R.styleable.CustomEditText_typeEditText)
            val editTextHint = getString(R.styleable.CustomEditText_editTextHint)
            val inputType = getEnum(R.styleable.CustomEditText_inputType, InputType.DEFAULT)


            when(inputType) {
                InputType.NAME -> {
                    binding.editText.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                }
                InputType.DEFAULT -> TODO()
                InputType.PASSWORD -> TODO()
                InputType.EMAIL -> TODO()
            }


            binding.typeEditText.text = typeEditText
            binding.editText.hint = editTextHint
        }

    }

}

public enum class InputType(i: Int) {
    DEFAULT(99),
    NAME(0),
    PASSWORD(1),
    EMAIL(2)
}

inline fun <reified T : Enum<T>> TypedArray.getEnum(index: Int, default: T) =
    getInt(index, -1).let {
        if (it >= 0) enumValues<T>()[it] else default
    }
