package com.luc.common.model.phonespecs

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Audio(
    val output: String = "3.5mm jack",
    val hasOutput: Boolean?
) : Parcelable
