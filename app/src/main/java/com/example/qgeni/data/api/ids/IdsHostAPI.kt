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
import java.net.InetSocketAddress
import java.net.Socket

object IdsHostAPI : IFullIdsAPI {
    private const val LOG_TAG = "IdsHostAPI"

    private var host = DefaultConnection.HOST
    private var port = DefaultConnection.genPort

    override fun createListeningPracticeItem(
        topicImageList: List<Bitmap>
    ): ObjectId? {
        try {
            val socket = Socket()
            socket.connect(InetSocketAddress(host, port), 3000)

            sendRequest(RequestType.IMG_FIND_SIMILAR_ONLY, socket, topicImageList)

            val response = getResponseForCreatingItem(socket)
            socket.close()

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
}
