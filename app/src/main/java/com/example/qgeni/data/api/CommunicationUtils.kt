package com.example.qgeni.data.api

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

object CommunicationUtils {

    fun encodeImage(image: Bitmap, quality: Int = 100): ByteArray {
        val outputStream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        return outputStream.toByteArray()
    }

    fun decodeImage(imageBytes: ByteArray): Bitmap? {
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

    fun intToBigEndianBytes(value: Int): ByteArray {
        return ByteBuffer.allocate(4)
            .order(ByteOrder.BIG_ENDIAN)
            .putInt(value)
            .array()
    }

    fun bigEndianBytesToInt(bytes: ByteArray): Int {
        return ByteBuffer.wrap(bytes)
            .order(ByteOrder.BIG_ENDIAN)
            .int
    }

    fun readNBytes(inputStream: InputStream, numBytes: Int): ByteArray {
        val buffer = ByteArray(numBytes)
        var bytesRead = 0
        while (bytesRead < numBytes) {
            val result = inputStream.read(buffer, bytesRead, numBytes - bytesRead)
            if (result == -1) break
            bytesRead += result
        }
        return buffer
    }

    fun encodeText(text: String): ByteArray {
        return text.toByteArray()
    }

}
