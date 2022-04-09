package com.luc.common.model.phonespecs

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Memory(
    val internal: List<String>? = null,
    val cardSlot: String? = null,
    val ram: List<String>? = null
): Parcelable

