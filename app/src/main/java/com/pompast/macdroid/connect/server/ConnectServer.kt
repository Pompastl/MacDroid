package com.pompast.macdroid.connect.server

import com.pompast.macdroid.connect.ConnectInterface
import java.io.InputStream
import java.io.OutputStream
import java.net.ServerSocket
import java.net.Socket

open class ConnectServer: ConnectInterface {
    private var serverSocket: ServerSocket? = null
    private var socket: Socket? = null

    private var inStream: InputStream? = null
    private var outStream: OutputStream? = null

    fun open(port: Int) {
        serverSocket = ServerSocket(port)
        socket = serverSocket!!.accept()

    }


    fun connectInput(): InputStream {
        val isValid = socket!!.isConnected
        if (isValid) {
            inStream = socket!!.getInputStream()
            return inStream!!
        }

        throw Exception("Client is not connected")

    }

    fun connectOutput(): OutputStream {
        val isValid = socket!!.isConnected
        if (isValid) {
            outStream = socket!!.getOutputStream()
            return outStream!!
        }

        throw Exception("Client is not connected")

    }

    override fun closeServer() {
         serverSocket?.close()
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
        return !serverSocket!!.isClosed
    }
}