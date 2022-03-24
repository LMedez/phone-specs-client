package com.luc.common.model.phonespecs

import android.provider.MediaStore.Audio
import java.util.*
import kotlin.collections.ArrayList

data class PhoneDetail(
    val thumbnail: String? = null,
    val name: String? = null,
    val dimension: String? = null,
    val phoneImages: ArrayList<String>? = null,
    val frontCamera: Camera? = null,
    val backCamera: Camera? = null,
    val wireless: Wireless? = null,
    val brand: String? = null,
    val models: List<String>? = null,
    val released: Date? = null,
    val software: Software? = null,
    val hardware: Hardware? = null,
    val price: List<String>? = null,
    val audio: Audio? = null,
    val display: Display? = null,
    val weight: String? = null,
)
