package com.luc.common.model.phonespecs

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Camera(
    val features: List<String>? = null,
    val video: List<String>? = null,
    val mp: List<String>? = null
):Parcelable
