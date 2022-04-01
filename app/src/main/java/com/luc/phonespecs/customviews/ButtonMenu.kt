package com.luc.phonespecs.customviews

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.ListPopupWindow
import androidx.core.content.withStyledAttributes
import com.luc.common.model.phonespecs.PhoneDetail
import com.luc.phonespecs.R
import com.luc.phonespecs.databinding.ButtonMenuBinding

class ButtonMenu @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = ButtonMenuBinding.inflate(LayoutInflater.from(context), this)

    private var onClick: ((String) -> Unit)? = null

    fun setOnClick(listener: (String) -> Unit) {
        onClick = listener
    }

    init {
        context.withStyledAttributes(attrs, R.styleable.ButtonMenu) {
            val textArray = getTextArray(R.styleable.ButtonMenu_android_entries)
            val text = getString(R.styleable.ButtonMenu_text)

            binding.textView.text = text

            val listPopupWindow = ListPopupWindow(context, null, R.attr.listPopupWindowStyle)
            listPopupWindow.anchorView = this@ButtonMenu

            val adapter = ArrayAdapter(context, R.layout.list_popup_window_item, textArray)
            listPopupWindow.setAdapter(adapter)

            setOnClickListener {
                listPopupWindow.show()
            }

            listPopupWindow.setOnItemClickListener { adapterView, view, position, l ->
                onClick?.let { click ->
                    click(textArray[position].toString())
                }

            }
        }
    }

}