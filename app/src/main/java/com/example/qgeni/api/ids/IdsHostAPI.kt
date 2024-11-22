package com.example.qgeni.api.ids

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.qgeni.api.CommunicationUtils
import com.example.qgeni.api.CommunicationUtils.DEFAULT_HOST
import com.example.qgeni.api.CommunicationUtils.DEFAULT_PORT
import com.example.qgeni.api.RequestType
import com.example.qgeni.api.ResponseType
import java.io.DataOutputStream
import java.net.Socket

object IdsHostAPI : IFullIdsAPI {
    private const val LOG_TAG = "IgsHostAPI"

    private const val IMG_PER_QUESTION = 4

    private var host = DEFAULT_HOST
    private var port = DEFAULT_PORT

    override suspend fun getSimilarImage(image: Bitmap, numDesiredImage: Int): List<Bitmap> {
        return performSocketOperation(
            requestType = RequestType.IMG_FIND_SIMILAR_ONLY,
            image = image,
            numItems = numDesiredImage
        ) { socket, numItems ->
            getResponseForFindSimilarOnly(socket, numItems)
        } ?: emptyList()
    }

    override suspend fun createQuestion(
        image: Bitmap,
        numQuestion: Int
    ): Pair<List<Bitmap>, List<String>> {
        return performSocketOperation(
            requestType = RequestType.IMG_FIND_AND_DESC,
            image = image,
            numItems = numQuestion
        ) { socket, numItems ->
            getResponseForFindAndDesc(socket, numItems)
        } ?: Pair(emptyList(), emptyList())
    }

    private fun <T> performSocketOperation(
        requestType: Int,
        image: Bitmap,
        numItems: Int,
        responseHandler: (Socket, Int) -> T
    ): T? {
        try {
            val socket = Socket(host, port)

            sendRequest(requestType, socket, image, numItems)

            val response = responseHandler(socket, numItems)

            socket.close()

            return response
        } catch (e: Exception) {
            Log.e(LOG_TAG, Log.getStackTraceString(e))
        }

        return null
    }


    private fun sendRequest(
        requestType: Int,
        socket: Socket,
        image: Bitmap,
        numImgOrQuestion: Int
    ) {
        val outputStream = DataOutputStream(socket.getOutputStream())

        val requestTypeByteArray = CommunicationUtils.intToBigEndianBytes(requestType)
        val numImgOrQuestionArray = CommunicationUtils.intToBigEndianBytes(numImgOrQuestion)
        val imageByteArray = CommunicationUtils.encodeImage(image)
        val imageSizeByteArray = CommunicationUtils.intToBigEndianBytes(imageByteArray.size)

        outputStream.write(requestTypeByteArray)
        outputStream.write(numImgOrQuestionArray)
        outputStream.write(imageSizeByteArray)
        outputStream.write(imageByteArray)
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


    private fun getResponseForFindAndDesc(
        socket: Socket,
        numQuestion: Int)
    : Pair<List<Bitmap>, List<String>> {
        val inputStream = socket.getInputStream()
        val imgList = getResponseForFindSimilarOnly(socket, numQuestion * IMG_PER_QUESTION)

        val desc = inputStream.bufferedReader().readText()
        val descList = desc.split("\n")

        return Pair(imgList, descList)
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
