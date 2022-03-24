package com.luc.phonespecs.customviews

import android.content.Context
import android.content.res.TypedArray
import android.text.InputType
import android.util.AttributeSet
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import androidx.core.widget.doOnTextChanged
import com.luc.phonespecs.R
import com.luc.phonespecs.databinding.CustomEditTextBinding

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
            val inputType = getEnum(R.styleable.CustomEditText_inputType, EditTextInputType.DEFAULT)
            when (inputType) {
                EditTextInputType.NAME -> {
                    binding.editText.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                }
                EditTextInputType.DEFAULT -> {}
                EditTextInputType.PASSWORD -> {
                    binding.editText.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD


                }
                EditTextInputType.EMAIL -> {
                    binding.editText.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                    var text = ""
                    binding.editText.setOnFocusChangeListener { view, hasFocused ->

                        if (!hasFocused) {
                            if (!text.isValidEmail())
                                Log.d("tests", "Invalid Email")
                        } else {
                            binding.editText.doOnTextChanged { texts, start, count, after ->
                                text = texts.toString()
                            }
                        }
                    }
                }
            }

            binding.typeEditText.text = typeEditText
            binding.editText.hint = editTextHint
        }

    }

}

enum class EditTextInputType(i: Int) {
    DEFAULT(0),
    NAME(1),
    PASSWORD(2),
    EMAIL(3)
}

inline fun <reified T : Enum<T>> TypedArray.getEnum(index: Int, default: T) =
    getInt(index, -1).let {
        if (it >= 0) enumValues<T>()[it] else default
    }

fun CharSequence?.isValidEmail() =
    !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()