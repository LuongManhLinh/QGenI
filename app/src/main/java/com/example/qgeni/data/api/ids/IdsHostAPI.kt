package com.example.qgeni.data.api.ids

import android.graphics.Bitmap
import android.util.Log
import com.example.qgeni.data.api.CommunicationUtils
import com.example.qgeni.data.api.ResponseType
import com.example.qgeni.data.DefaultConnection
import com.example.qgeni.data.api.RequestType
import com.example.qgeni.data.preferences.UserPreferenceManager
import org.bson.types.ObjectId
import java.io.DataOutputStream
import java.net.Socket

object IdsHostAPI : IFullIdsAPI {
    private const val LOG_TAG = "IdsHostAPI"

    private var host = DefaultConnection.HOST
    private var port = DefaultConnection.genPort

    override suspend fun createListeningPracticeItem(
        topicImageList: List<Bitmap>
    ): ObjectId? {
        try {
            val socket = Socket(host, port)
            Log.d(LOG_TAG, "$host:$port")

            sendRequest(RequestType.IMG_FIND_SIMILAR_ONLY, socket, topicImageList)

            val response = getResponseForCreatingItem(socket)
            socket.close()

            Log.d(LOG_TAG, "Response: $response")

            return response

        } catch (e: Exception) {
            Log.e(LOG_TAG, Log.getStackTraceString(e))
            return null
        }
    }


    private fun sendRequest(
        requestType: Int,
        socket: Socket,
        topicImageList: List<Bitmap>
    ) {
        val outputStream = DataOutputStream(socket.getOutputStream())

        // Send the request type
        outputStream.write(CommunicationUtils.intToBigEndianBytes(requestType))

        // Send the user id as string
        val userId = UserPreferenceManager.getUserId()!!.toHexString()
        val userIdByteArray = CommunicationUtils.encodeText(userId)
        val userIdSizeByteArray = CommunicationUtils.intToBigEndianBytes(userIdByteArray.size)
        outputStream.write(userIdSizeByteArray)
        outputStream.write(userIdByteArray)

        // Send the number of topic images
        outputStream.write(CommunicationUtils.intToBigEndianBytes(topicImageList.size))

        // Send the topic images
        for (topicImage in topicImageList) {
            val imgByteArray = CommunicationUtils.encodeImage(topicImage, quality = 70)
            val imgSizeByteArray = CommunicationUtils.intToBigEndianBytes(imgByteArray.size)
            outputStream.write(imgSizeByteArray)
            outputStream.write(imgByteArray)
        }
    }

    private fun getResponseForCreatingItem(socket: Socket) : ObjectId? {
        val inputStream = socket.getInputStream()
        val bArr4b = CommunicationUtils.readNBytes(inputStream, 4)
        val responseType = CommunicationUtils.bigEndianBytesToInt(bArr4b)
        if (responseType != ResponseType.SUCCESS) {
            Log.e(LOG_TAG, "Server status: $responseType")
            return null
        }
        val itemId = inputStream.bufferedReader().readText().trim()

        return ObjectId(itemId)
    }


    private fun getResponseForFindSimilarOnly(socket: Socket, numDesiredImage: Int) : List<Bitmap> {
        val inputStream = socket.getInputStream()
        val outputStream = socket.getOutputStream()

        // Read the response type
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
                val img = CommunicationUtils.decodeImage(imgBArr)
                if (img == null) {
                    val rpBytes = CommunicationUtils.intToBigEndianBytes(ResponseType.CLIENT_ERROR)
                    outputStream.write(rpBytes)
                } else {
                    imgList.add(img)
                    val rpBytes = CommunicationUtils.intToBigEndianBytes(ResponseType.SUCCESS)
                    outputStream.write(rpBytes)
                    break
                }
            }
        }

        return imgList
    }


    fun setHostPort(host: String, port: Int) {
        IdsHostAPI.host = host
        IdsHostAPI.port = port
    }


    fun resetHostPort() {
        host = DefaultConnection.HOST
        port = DefaultConnection.genPort
    }
}
