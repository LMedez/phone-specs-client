package com.luc.common.model.phonespecs

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Wireless(
    val bluetooth: List<String>? = null,
    val wifi: List<String>? = null,
    val usb: List<String>? = null
): Parcelable

