package com.luc.common.model.phonespecs

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Hardware(

    val processor: Processor? = null,
    val battery: Battery? = null,
    val memory: Memory? = null
): Parcelable

