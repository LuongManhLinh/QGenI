package com.example.qgeni.data.api.qgs

import android.util.Log
import com.example.qgeni.data.DefaultConnection
import com.example.qgeni.data.api.CommunicationUtils
import com.example.qgeni.data.api.RequestType
import com.example.qgeni.data.api.ResponseType
import com.example.qgeni.data.model.ReadingQuestion
import com.example.qgeni.data.preferences.UserPreferenceManager
import org.bson.types.ObjectId
import java.io.DataOutputStream
import java.net.Socket

object QgsHostChecker {
    private const val LOG_TAG = "QgsHostChecker"

    private var host = DefaultConnection.HOST
    private var port = DefaultConnection.genPort

    fun validate(passage: String, questionList: List<ReadingQuestion>): ObjectId? {
        try {
            val socket = Socket(host, port)

            sendRequest(RequestType.TFN_CHECK, socket, passage, questionList)

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
        passage: String,
        questionList: List<ReadingQuestion>
    ) {
        val outputStream = DataOutputStream(socket.getOutputStream())

        // Send the request type
        outputStream.write(CommunicationUtils.intToBigEndianBytes(requestType))

        // Send the user id as string
        val userId = UserPreferenceManager.getUserId()!!.toHexString()
        sendString(outputStream, userId)

        // Send the passage
        sendString(outputStream, passage)

        // Send the number of statements
        outputStream.write(CommunicationUtils.intToBigEndianBytes(questionList.size))

        // Send the statements
        for (question in questionList) {
            sendString(outputStream, question.statement)
        }

        // Send the type of each statements
        for (question in questionList) {
            outputStream.write(CommunicationUtils.intToBigEndianBytes(question.answer.toInt()))
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


    private fun sendString(outputStream: DataOutputStream, string: String) {
        val stringByteArray = CommunicationUtils.encodeText(string)
        val stringSizeByteArray = CommunicationUtils.intToBigEndianBytes(stringByteArray.size)
        outputStream.write(stringSizeByteArray)
        outputStream.write(stringByteArray)
    }
}