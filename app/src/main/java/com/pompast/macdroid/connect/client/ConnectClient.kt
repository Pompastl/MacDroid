package com.pompast.macdroid.connect.client

import com.pompast.macdroid.connect.ConnectInterface
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket

open class ConnectClient: ConnectInterface {
    private var client: Socket? = null
    private var inStream: InputStream? = null
    private var outStream: OutputStream? = null

    fun open(ip: String, port: Int) {
        client = Socket(ip, port)
    }

    protected fun connectInput(): InputStream {
        val isValid = client != null && client!!.isConnected
        if (isValid) {
            inStream = client!!.getInputStream()
            return inStream!!
        }

        throw Exception("Client is not connected")

    }

    protected fun connectOutput(): OutputStream {
        val isValid = client != null && client!!.isConnected
        if (isValid) {
            outStream = client!!.getOutputStream()
            return outStream!!
        }

        throw Exception("Client is not connected")

    }


    override fun closeServer() {
        client?.close()
    }

    override fun closeOutStream() {
        if (outStream != null) {
            outStream!!.close()
        }
    }

    override fun closeInpStream() {
        if (inStream != null) {
            inStream!!.close()
        }
    }

    override fun closeCommunication() {
        closeInpStream()
        closeOutStream()
    }

    override fun closeMain() {
        closeCommunication()
        closeServer()
    }

    override fun isOpen(): Boolean {
        return !client!!.isClosed
    }
}