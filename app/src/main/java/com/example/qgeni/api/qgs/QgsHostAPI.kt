package com.example.qgeni.api.qgs

import android.util.Log
import com.example.qgeni.api.CommunicationUtils
import com.example.qgeni.api.CommunicationUtils.DEFAULT_HOST
import com.example.qgeni.api.CommunicationUtils.DEFAULT_PORT
import com.example.qgeni.api.RequestType
import com.example.qgeni.api.ResponseType
import com.example.qgeni.api.ids.IdsHostAPI


import java.io.DataOutputStream
import java.net.Socket

object QgsHostAPI : IQgsAPI {

    private const val LOG_TAG = "QgsHostAPI"

    private var host = DEFAULT_HOST
    private var port = DEFAULT_PORT

    override suspend fun generateQuestions(paragraph: String, numQuestions: Int): List<QgsForm> {
        try {
            val socket = Socket(host, port)

            sendRequest(socket, paragraph, numQuestions)

            val response = getResponse(socket)

            socket.close()

            return response
        } catch (e: Exception) {
            Log.e(LOG_TAG, Log.getStackTraceString(e))
        }

        return emptyList()
    }

    private fun sendRequest(socket: Socket, paragraph: String, numQuestions: Int) {
        val outputStream = DataOutputStream(socket.getOutputStream())

        val requestTypeByteArray = CommunicationUtils.intToBigEndianBytes(RequestType.TFN)
        val numQuestionsByteArray = CommunicationUtils.intToBigEndianBytes(numQuestions)
        val paragraphByteArray = CommunicationUtils.encodeText(paragraph)
        val paragraphLengthByteArray = CommunicationUtils.intToBigEndianBytes(paragraphByteArray.size)

        outputStream.write(requestTypeByteArray)
        outputStream.write(numQuestionsByteArray)
        outputStream.write(paragraphLengthByteArray)
        outputStream.write(paragraphByteArray)
    }

    private fun getResponse(socket: Socket) : List<QgsForm> {
        val inputStream = socket.getInputStream()
        val outputStream = socket.getOutputStream()

        // Read the response type
        var bArr4b = CommunicationUtils.readNBytes(inputStream, 4)

        val responseType = CommunicationUtils.bigEndianBytesToInt(bArr4b)

        if (responseType != ResponseType.SUCCESS) {
            Log.e(LOG_TAG, "Server status: $responseType")
            return emptyList()
        }

        bArr4b = CommunicationUtils.readNBytes(inputStream, 4)
        val numTrue = CommunicationUtils.bigEndianBytesToInt(bArr4b)

        bArr4b = CommunicationUtils.readNBytes(inputStream, 4)
        val numFalse = CommunicationUtils.bigEndianBytesToInt(bArr4b)

        val allStatements = inputStream.bufferedReader().readText().split("\n")

        val output = mutableListOf<QgsForm>()

        for (idx in allStatements.indices) {
            val statement = allStatements[idx]
            val answer = when {
                idx < numTrue -> "True"
                idx >= numTrue && idx < numTrue + numFalse -> "False"
                else -> "Not Given"
            }
            output.add(QgsForm(statement, answer))
        }

        return output
    }
}