package com.roo.home.mvp.utils

import com.google.protobuf.ByteString
import org.web3j.utils.Numeric



fun String.toHexBytes(): kotlin.ByteArray? {
    return Numeric.hexStringToByteArray(this)
}

fun String.toHexByteArray(): kotlin.ByteArray? {
    return Numeric.hexStringToByteArray(this)
}

fun String.toByteString(): ByteString {
    return ByteString.copyFrom(this, Charsets.UTF_8)
}

fun String.toHexBytesInByteString(): ByteString {
    return ByteString.copyFrom(this.toHexBytes())
}