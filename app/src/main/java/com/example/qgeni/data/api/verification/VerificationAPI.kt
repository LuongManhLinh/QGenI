package com.example.qgeni.data.api.verification

import android.util.Log
import com.example.qgeni.data.DefaultConnection
import com.example.qgeni.data.api.CommunicationUtils
import com.example.qgeni.data.api.RequestType
import com.example.qgeni.utils.ContextConstants
import java.net.Socket

object VerificationAPI {
    private const val LOG_TAG = "VerificationAPI"

    private var host = DefaultConnection.host
    private var port = DefaultConnection.genPort

    fun sendVerification(email: String) {
        try {
            val socket = Socket(host, port)

            val outputStream = socket.getOutputStream()
            outputStream.write(CommunicationUtils.intToBigEndianBytes(RequestType.VERIFICATION))

            val emailByteArray = CommunicationUtils.encodeText(email)
            outputStream.write(CommunicationUtils.intToBigEndianBytes(emailByteArray.size))
            outputStream.write(emailByteArray)

            val serverStatus = CommunicationUtils.bigEndianBytesToInt(
                CommunicationUtils.readNBytes(socket.getInputStream(), 4)
            )

            Log.e(LOG_TAG, "Server status: $serverStatus")

            val otpList = mutableListOf<Int>()
            val inputStream = socket.getInputStream()
            for (i in 0..3) {
                val bArr4b = CommunicationUtils.readNBytes(inputStream, 4)
                val otpNum = CommunicationUtils.bigEndianBytesToInt(bArr4b)
                otpList.add(otpNum)
            }

            ContextConstants.setOtp(otpList)
            ContextConstants.setCurrentEmail(email)
            Log.e(LOG_TAG, "OTP: $otpList")

        } catch (e: Exception) {
            Log.e(LOG_TAG, Log.getStackTraceString(e))
        }
    }

    fun verify(otp: List<Int>) : Boolean {
        return ContextConstants.checkOtp(otp)
    }
}