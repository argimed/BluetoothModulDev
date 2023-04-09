package com.example.bt_def.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException
import java.util.*


class ConnectThread(device: BluetoothDevice) : Thread() {
//    private val uuid = "00002901-0000-1000-8000-00805f9b34fb" //HMSoft
    private val uuid = "0000ffe0-0000-1000-8000-00805f9b34fb"  //HMSoft
    private var mSocket: BluetoothSocket? = null
    init {
        try {
            mSocket = device.createRfcommSocketToServiceRecord(UUID.fromString(uuid))
        } catch (e: IOException){

        } catch (se: SecurityException){

        }
    }

    override fun run() {
        try {
            Log.d("MyLog","Connecting...")
            mSocket?.connect()
            Log.d("MyLog","Connected")
        } catch (e: IOException){
            Log.d("MyLog","Not Connected")
        } catch (se: SecurityException){

        }
    }

    fun closeConnection(){
        try {
            mSocket?.close()
        } catch (e: IOException){

        }
    }
}