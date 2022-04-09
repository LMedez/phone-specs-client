package com.luc.common.model.phonespecs

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Software(
    val os: String? = null,
    val osVersion: String? = null
): Parcelable


