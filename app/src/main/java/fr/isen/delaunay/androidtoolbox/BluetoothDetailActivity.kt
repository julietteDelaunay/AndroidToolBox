package fr.isen.delaunay.androidtoolbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothProfile
import android.util.Log
import kotlinx.android.synthetic.main.activity_bluetooth_detail.*

class BluetoothDetailActivity : AppCompatActivity() {

        private var connectionState = STATE_DISCONNECTED
        private var bluetoothGatt: BluetoothGatt? = null
        private var TAG:String =  "MyActivity"


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_bluetooth_detail)

            val device : BluetoothDevice? = intent.getParcelableExtra("ble_device")
            bluetoothGatt = device?.connectGatt(this, true, gattCallback)
            Log.i(TAG, "BluetoothDetailActivity")

        }

    private val gattCallback = object : BluetoothGattCallback(){
        override fun onConnectionStateChange(
            gatt : BluetoothGatt,
            status: Int,
            newState: Int

        ){
            Log.i(TAG, "gattCallback")
            val intentAction: String
            when (newState){
                BluetoothProfile.STATE_CONNECTED -> {
                    runOnUiThread {
                        textStatus.text = STATE_CONNECTED
                    }
                    bluetoothGatt?.discoverServices()
                    Log.i(TAG, "Attempting to start service discovery :" +bluetoothGatt?.discoverServices())
                }
                BluetoothProfile.STATE_DISCONNECTED -> {
                    runOnUiThread {
                        textStatus.text = STATE_DISCONNECTED
                    }
                    bluetoothGatt?.discoverServices()
                    Log.i(TAG, "deconnecté")
                }
            }
        }


        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            super.onServicesDiscovered(gatt, status)
            gatt?.services?.forEach {
                Log.i(TAG, "Service détecté : ${it.uuid}")
            } //on obtient une liste de services

        }
    }


        companion object{
            private const val STATE_DISCONNECTED = "déconnecté"
            private const val STATE_CONNECTING = 1
            private const val STATE_CONNECTED = "Connecté"
            const val ACTION_GATT_CONNECTED = "com.example.bluetooth.le.ACTION_GATT_CONNECTED"
            const val ACTION_GATT_DISCONNECTED = "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED"
            const val ACTION_GATT_SERVICES_DISCOVERED = "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED"
            const val ACTION_DATA_AVAILABLE = "com.example.bluetooth.le.ACTION_DATA_AVAILABLE"
            const val EXTRA_DATA = "com.example.bluetooth.le.EXTRA_DATA"
        }

    }
