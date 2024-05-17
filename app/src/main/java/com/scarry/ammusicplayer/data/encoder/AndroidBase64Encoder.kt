package com.scarry.ammusicplayer.data.encoder

import android.util.Base64

class AndroidBase64Encoder : Base64Encoder {
    override fun encodeToString(
        input: ByteArray
    ): String = Base64.encodeToString(input, android.util.Base64.NO_WRAP)
}