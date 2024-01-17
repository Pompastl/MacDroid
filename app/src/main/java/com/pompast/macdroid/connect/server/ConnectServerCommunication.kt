package com.pompast.macdroid.connect.server

import java.io.DataInputStream
import java.io.DataOutputStream

class ConnectServerCommunication : ConnectServer() {
    private var dos: DataOutputStream? = null
    private var dis: DataInputStream? = null

    fun send(str: String) {
        val byte = str.toByteArray()
        send(byte)
    }

    fun send(b: ByteArray) {
        if (dos == null)
            dos = DataOutputStream(connectOutput())

        dos!!.write(b)
        dos!!.flush()
    }

    fun get(): ByteArray {
        if (dis == null)
            dis = DataInputStream(connectInput())

        return waitByteArray(dis!!)

    }

    private fun waitByteArray(dis: DataInputStream): ByteArray {
        while (true) {      //Thanks to https://chat.openai.com
            val availableBytes = dis.available()
            if (availableBytes > 0) {
                val byteArray = ByteArray(availableBytes)
                dis.readFully(byteArray)
                return byteArray
            }

        }
    }

}