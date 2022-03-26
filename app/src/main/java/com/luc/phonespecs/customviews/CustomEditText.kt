package com.luc.phonespecs.customviews

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.text.InputFilter
import android.text.InputType
import android.util.AttributeSet
import android.util.Patterns
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import com.luc.phonespecs.R
import com.luc.phonespecs.databinding.CustomEditTextBinding
import com.luc.phonespecs.utils.getDrawableOrNull

class CustomEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = CustomEditTextBinding.inflate(LayoutInflater.from(context), this)
    private var passwordVisibility = false

    init {
        context.withStyledAttributes(attrs, R.styleable.CustomEditText) {
            val typeEditText = getString(R.styleable.CustomEditText_typeEditText)
            val editTextHint = getString(R.styleable.CustomEditText_editTextHint)
            val inputType = getEnum(R.styleable.CustomEditText_inputType, EditTextInputType.DEFAULT)
            if (inputType != EditTextInputType.PASSWORD)
                binding.passwordVisibility.visibility = INVISIBLE


            when (inputType) {
                EditTextInputType.DEFAULT -> {
                    binding.editText.filters = arrayOf(InputFilter.LengthFilter(15))
                }

                EditTextInputType.NAME -> {
                    with(binding) {
                        editText.filters = arrayOf(InputFilter.LengthFilter(15))
                        editText.inputType =
                            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                        editText.setOnFocusChangeListener { _, isFocused ->
                            if (!isFocused) {
                                if (editText.text.isEmpty()) {
                                    editText.error = "Empty Name"
                                }
                            }
                        }
                    }
                }

                EditTextInputType.PASSWORD -> {
                    with(binding) {
                        editText.setPadding(24.toPx(), 0, 42.toPx(), 0)
                        editText.inputType =
                            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

                        editText.setOnFocusChangeListener { _, isFocused ->
                            if (!isFocused) {
                                if (!isValidPassword(editText.text.toString())) {
                                    editText.error = "Invalid Password"
                                }
                            }
                        }
                    }
                }

                EditTextInputType.EMAIL -> {
                    with(binding) {
                        editText.inputType =
                            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                        editText.setOnFocusChangeListener { _, isFocused ->
                            if (!isFocused) {
                                if (!editText.text.toString().isValidEmail()) {
                                    editText.error = "Invalid Email"
                                }
                            }
                        }
                    }
                }
            }

            binding.passwordVisibility.setOnClickListener {
                passwordVisibility = !passwordVisibility
                if (passwordVisibility) {
                    binding.editText.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    it.background = context.getDrawableOrNull(R.drawable.ic_visibility)
                } else {
                    binding.editText.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    it.background =
                        context.getDrawableOrNull(R.drawable.ic_visibility_off)
                }
            }

            binding.typeEditText.text = typeEditText
            binding.editText.hint = editTextHint
        }
    }

    fun setTypeEditText(typeEditTExt: String) {
        binding.typeEditText.text = typeEditTExt
    }


    fun editTextHasError(): Boolean {
        binding.editText.clearFocus()
        if (binding.editText.text.isEmpty()) return true
        return binding.editText.error != null
    }

    fun getEditText(): EditText {
        return binding.editText
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

fun isValidPassword(password: String?): Boolean {
    password?.let {
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$"
        val passwordMatcher = Regex(passwordPattern)

        return passwordMatcher.find(password) != null
    } ?: return false
}

fun Number.toPx() = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(),
    Resources.getSystem().displayMetrics
).toInt()