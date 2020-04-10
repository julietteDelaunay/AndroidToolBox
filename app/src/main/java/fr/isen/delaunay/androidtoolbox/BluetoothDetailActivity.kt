package fr.isen.delaunay.androidtoolbox

import android.bluetooth.*
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_bluetooth_detail.*

class BluetoothDetailActivity : AppCompatActivity() {
private var bluetoothGatt: BluetoothGatt? = null
    private var TAG: String = "services"
    private lateinit var adapter: BluetoothDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth_detail)

        val device: BluetoothDevice = intent.getParcelableExtra("ble_device")
        textName.text = device.name
        bluetoothGatt = device.connectGatt(this, true, gattCallback)
    }

    //apk

    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(
            gatt: BluetoothGatt,
            status: Int,
            newState: Int
        ) {
            when (newState) {
                BluetoothProfile.STATE_CONNECTED -> {
                    runOnUiThread {
                        textStatus.text = STATE_CONNECTED
                        //name.text = device?.name
                    }
                    bluetoothGatt?.discoverServices()
                    Log.i(TAG, "Connecté")
                }
                BluetoothProfile.STATE_DISCONNECTED -> {
                    runOnUiThread {
                        textStatus.text = STATE_DISCONNECTED
                    }
                    Log.i(TAG, "deconnecté")
                }
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            super.onServicesDiscovered(gatt, status)
            runOnUiThread {
               RecyclerBLE.adapter = BluetoothDetailAdapter(
                    gatt?.services?.map {
                        BLEService(
                            it.uuid.toString(),
                            it.characteristics
                        )
                    }?.toMutableList() ?: arrayListOf()
                    , this@BluetoothDetailActivity, gatt)
                RecyclerBLE.layoutManager = LinearLayoutManager(this@BluetoothDetailActivity)
                gatt?.services?.forEach {
                    Log.i(TAG, "Service détecté : ${it.uuid}")
                } //on obtient une liste de services
            }
        }

        override fun onCharacteristicRead(
            gatt: BluetoothGatt?,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {
            val value = characteristic.getStringValue(0)
            Log.e(
                "TAG",
                "onCharacteristicRead: " + value + " UUID " + characteristic.uuid.toString()
            )
            runOnUiThread {
                RecyclerBLE.adapter?.notifyDataSetChanged()
            }
        }

        override fun onCharacteristicWrite(
            gatt: BluetoothGatt?,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {
            val value = characteristic.value
            Log.e(
                "TAG",
                "onCharacteristicWrite: " + value + " UUID " + characteristic.uuid.toString()
            )
            runOnUiThread {
                RecyclerBLE.adapter?.notifyDataSetChanged()
            }
        }

        override fun onCharacteristicChanged(
            gatt: BluetoothGatt?,
            characteristic: BluetoothGattCharacteristic
        ) {
            val value = byteArrayToHexString(characteristic.value)
            Log.e(
                "TAG",
                "onCharacteristicChanged: " + value + " UUID " + characteristic.uuid.toString()
            )
            runOnUiThread {
                RecyclerBLE.adapter?.notifyDataSetChanged()
            }
        }
    }

    private fun byteArrayToHexString(array: ByteArray): String {
        val result = StringBuilder(array.size * 2)
        for ( byte in array ) {
            val toAppend = String.format("%X", byte) // hexadecimal
            result.append(toAppend).append("-")
        }
        result.setLength(result.length - 1) // remove last '-'
        return result.toString()
    }

    override fun onStop() {
        super.onStop()
        bluetoothGatt?.close()
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
