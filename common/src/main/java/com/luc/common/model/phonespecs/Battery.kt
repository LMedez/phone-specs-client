package com.luc.common.model.phonespecs

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Battery(
    val type: String? = null,
    val life: String? = null,
    val chargingPower: String? = null
):Parcelable

