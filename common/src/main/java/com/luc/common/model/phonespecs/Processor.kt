package com.luc.common.model.phonespecs

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Processor(
    val CPU: String? = null,
    val chipset: String? = null,
    val GPU: String? = null
): Parcelable

