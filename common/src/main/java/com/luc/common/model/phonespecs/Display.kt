package com.luc.common.model.phonespecs

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Display(
    val type: String? = null,
    val hz: String? = null,
    val aspectRatio: String? = null,
    val inch: String? = null,
    val ppi: Int = 0,
    val resolution: String? = null
):Parcelable


