package com.example.qgeni.api

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

object CommunicationUtils {
    fun encodeImage(image: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        return outputStream.toByteArray()
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


}
