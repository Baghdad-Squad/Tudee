package com.baghdad.tudee.ui.utils.image

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter

fun byteArrayToPainter(byteArray: ByteArray): Painter? {
    return try {
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        BitmapPainter(bitmap.asImageBitmap())
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}