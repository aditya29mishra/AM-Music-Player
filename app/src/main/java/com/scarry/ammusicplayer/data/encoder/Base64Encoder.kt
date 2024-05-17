package com.scarry.ammusicplayer.data.encoder

fun interface Base64Encoder {
fun encodeToString(input: ByteArray): String
}