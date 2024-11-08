package com.example.qgeni.api.ids

import android.graphics.Bitmap
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.IOException
import java.net.Socket
import java.net.UnknownHostException
import java.nio.ByteBuffer
import java.nio.ByteOrder


object IdsAPI : IIdsApi {
    const val DEFAULT_HOST = "10.10.106.117"
    const val DEFAULT_PORT = 20000
    private const val LOG_TAG = "SOCKET CONNECTION"

    private var host = DEFAULT_HOST
    private var port = DEFAULT_PORT


    override fun describe(image: Bitmap): String? {
        try {
            val socket = Socket(host, port)
            val outputStream = DataOutputStream(socket.getOutputStream())

            val imageByteArray = encodeImage(image)
            val imageSizeByteArray = intToBigEndianBytes(imageByteArray.size)

            outputStream.write(imageSizeByteArray)
            outputStream.write(imageByteArray)

            Log.d(LOG_TAG, "Sent ${imageByteArray.size} bytes data")

            val inputStream = socket.getInputStream()
            val response = inputStream.bufferedReader().readLine()

            socket.close()
            return response

        } catch (e: Exception) {
            when (e) {
                is IllegalArgumentException -> Log.e(LOG_TAG, "ILLEGAL ARGUMENT $e")

                is UnknownHostException -> Log.e(LOG_TAG, "UNKNOWN HOST $e")

                is IOException -> Log.e(LOG_TAG, "IOEXCEPTION $e")

                else -> Log.e(LOG_TAG, "EXCEPTION $e")
            }

            Log.e(
                LOG_TAG,
                "You may need to set HOST and PORT! \n" +
                        "Use IdsApi.setHostPort(host, port) and try it again"
            )
        }

       return null
    }


    private fun encodeImage(image: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        return outputStream.toByteArray()
    }

    private fun intToBigEndianBytes(value: Int): ByteArray {
        val byteBuffer = ByteBuffer.allocate(4)
        byteBuffer.order(ByteOrder.BIG_ENDIAN)
        byteBuffer.putInt(value)
        return byteBuffer.array()
    }


    fun setHostPort(host: String, port: Int) {
        this.host = host
        this.port = port
    }


    fun resetHostPort() {
        host = DEFAULT_HOST
        port = DEFAULT_PORT
    }
}
