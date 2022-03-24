package com.luc.common.model.phonespecs

data class Wireless(
    val bluetooth: List<String>? = null,
    val wifi: List<String>? = null,
    val usb: List<String>? = null
)

