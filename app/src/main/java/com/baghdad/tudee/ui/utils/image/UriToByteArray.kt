package com.baghdad.tudee.ui.utils.image

import android.content.Context
import android.net.Uri
import java.io.IOException

fun uriToByteArray(context: Context, uri: Uri?): ByteArray? {
    if (uri == null) return null

    return try {
        context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}
