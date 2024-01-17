package com.pompast.macdroid.connect

interface ConnectInterface {
    fun closeServer()

    fun closeOutStream()
    fun closeInpStream()

    fun closeCommunication()
    fun closeMain()

    fun isOpen(): Boolean

}