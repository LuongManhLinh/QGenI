package com.example.qgeni.api.ids

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.qgeni.api.CommunicationUtils
import com.example.qgeni.api.RequestType
import com.example.qgeni.api.ResponseType
import java.io.DataOutputStream
import java.net.Socket

object IdsAPI : IFullIdsAPI {
    const val DEFAULT_HOST = "192.168.1.173"
    const val DEFAULT_PORT = 20000
    private const val LOG_TAG = "IgsHostAPI"

    private var host = DEFAULT_HOST
    private var port = DEFAULT_PORT

    override suspend fun getSimilarImage(image: Bitmap, numDesiredImage: Int): List<Bitmap> {
        try {
            val socket = Socket(host, port)

            sendRequest(socket, image, numDesiredImage)

            val response = getResponse(socket, numDesiredImage)

            socket.close()

            return response

        } catch (e: Exception) {
            Log.e(LOG_TAG, Log.getStackTraceString(e))
        }

        return emptyList()
    }

    private fun sendRequest(socket: Socket, image: Bitmap, numDesiredImage: Int) {
        val outputStream = DataOutputStream(socket.getOutputStream())

        val requestTypeByteArray = CommunicationUtils.intToBigEndianBytes(RequestType.IMG_FIND_SIMILAR_ONLY)
        val numDesiredImageByteArray = CommunicationUtils.intToBigEndianBytes(numDesiredImage)
        val imageByteArray = CommunicationUtils.encodeImage(image)
        val imageSizeByteArray = CommunicationUtils.intToBigEndianBytes(imageByteArray.size)

        outputStream.write(requestTypeByteArray)
        outputStream.write(numDesiredImageByteArray)
        outputStream.write(imageSizeByteArray)
        outputStream.write(imageByteArray)

        Log.d(
            LOG_TAG,
            "Sent ${requestTypeByteArray.size} +" +
                    " ${numDesiredImageByteArray.size} +" +
                    " ${imageSizeByteArray.size} +" +
                    " ${imageByteArray.size}" +
                    " bytes data"
        )
    }


    private fun getResponse(socket: Socket, numDesiredImage: Int) : List<Bitmap> {
        val inputStream = socket.getInputStream()
        val outputStream = socket.getOutputStream()

        var bArr4b = CommunicationUtils.readNBytes(inputStream, 4)

        val responseType = CommunicationUtils.bigEndianBytesToInt(bArr4b)

        if (responseType != ResponseType.SUCCESS) {
            Log.e(LOG_TAG, "Server status: $responseType")
            return emptyList()
        }

        val imgList = mutableListOf<Bitmap>()

        for (idx in 0 until numDesiredImage) {
            bArr4b = CommunicationUtils.readNBytes(inputStream, 4)
            while (true) {
                val imgBLen = CommunicationUtils.bigEndianBytesToInt(bArr4b)
                val imgBArr = CommunicationUtils.readNBytes(inputStream, imgBLen)
                val img = BitmapFactory.decodeByteArray(imgBArr, 0, imgBLen)
                if (img == null) {
                    val rpBytes = CommunicationUtils.intToBigEndianBytes(ResponseType.CLIENT_ERROR)
                    outputStream.write(rpBytes)
                    Log.e(LOG_TAG, "Error converting image at index ${idx + 1}")
                } else {
                    imgList.add(img)
                    val rpBytes = CommunicationUtils.intToBigEndianBytes(ResponseType.SUCCESS)
                    outputStream.write(rpBytes)
                    Log.d(LOG_TAG, "Successfully received image at index ${idx + 1}")
                    break
                }
            }
        }

        return imgList
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
