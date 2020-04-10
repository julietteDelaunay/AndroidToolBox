package fr.isen.delaunay.androidtoolbox

//import android.annotation.SuppressLint
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_b_l_e_scan.*


class BLEScanActivity : AppCompatActivity() {
    private lateinit var handler: Handler
    private var mScanning: Boolean = false
    private lateinit var adapter: BLEAdapterActivity
    private val devices = ArrayList<ScanResult>()

    private val bluetoothAdapter: BluetoothAdapter? by lazy(LazyThreadSafetyMode.NONE) {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    private val isBLEEnabled: Boolean
        get() = bluetoothAdapter?.isEnabled == true

    override fun onCreate(savedInstanceState: Bundle?) {


        //
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_l_e_scan)
       // progressBar.progress = (progressBar.progress + 5) % 100
        bleTextfailed.visibility = View.GONE

        BLErecycler.adapter = BLEAdapterActivity(devices, ::onDeviceClicked)
        BLErecycler.layoutManager = LinearLayoutManager(this)
        textScanBLE.setOnClickListener {
            when {
                isBLEEnabled -> {
                        //init scan
                        initBLEScan()
                        initScan()
                        togglePlayPauseAction()

                }
                bluetoothAdapter != null -> {
                    //ask for permission
                    val enableBTIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    startActivityForResult(enableBTIntent, REQUEST_ENABLE_BT)
                }
                else -> {
                    //device is not compatible with your device
                    bleTextfailed.visibility = View.VISIBLE
                }
            }
        }

    }


    private fun initScan() {
        progressBar.visibility = View.VISIBLE
        dividerBle.visibility = View.GONE

        handler = Handler()
        scanLeDevice(true)
    }

    private fun scanLeDevice(enable: Boolean) {
        bluetoothAdapter?.bluetoothLeScanner?.apply {

            if (enable) {
                Toast.makeText(applicationContext, "scann le device lancé ", Toast.LENGTH_SHORT)
                    .show()
                Log.w("BLEScanActivity", "Scanning for devices")
                handler.postDelayed({
                    Log.w("BLEScanActivity", "part 1")
                    mScanning = false
                    stopScan(leScanCallback)
                }, SCAN_PERIOD)
                mScanning = true
                startScan(leScanCallback)
                adapter.clearResults()
                adapter.notifyDataSetChanged()
                //  progressBar.progress = (progressBar.progress + 5) % 100
                BarProress()
                //
                Log.w("BLEScanActivity", "part 0")

            } else {
                Log.w("BLEScanActivity", "part 2")
                mScanning = false
                stopScan(leScanCallback)
            }
        }
    }

    private val leScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            Log.w("BLEScanActivity", "onResult()")
           // Log.w("BLEScanActivity", "Results: ${result?.device}")
            runOnUiThread {
                adapter.addDeviceToList(result)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun initBLEScan() {
        adapter = BLEAdapterActivity(
            arrayListOf(),
            ::onDeviceClicked
        )
        BLErecycler.adapter = adapter
        BLErecycler.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        handler = Handler()

        scanLeDevice(true)
        textScanBLE.setOnClickListener {
            Toast.makeText(this, "scann lancé", Toast.LENGTH_SHORT).show()
            scanLeDevice(!mScanning)
        }

    }

    private fun onDeviceClicked(device: BluetoothDevice) {
        val intent = Intent(this, BluetoothDetailActivity::class.java)
        intent.putExtra("ble_device", device)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                if (isBLEEnabled) {
                    Toast.makeText(this, "Bluetooth has been enabled", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Bluetooth has been disabled", Toast.LENGTH_SHORT).show()
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Bluetooth enabling has been canceled", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    companion object {
        private const val SCAN_PERIOD: Long = 10000
        private const val REQUEST_ENABLE_BT = 44
    }

    @SuppressLint("SetTextI18n")
    private fun togglePlayPauseAction() {
        if (mScanning) {
            progressBar.visibility = View.VISIBLE
            dividerBle.visibility = View.INVISIBLE
            textScanBLE.text = "scan BLE en cours"
            imgPlay.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    android.R.drawable.ic_media_pause
                )
            )
        } else {
            progressBar.visibility = View.INVISIBLE
            dividerBle.visibility = View.VISIBLE
            textScanBLE.text = "lancer le scan BLE"
            imgPlay.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    android.R.drawable.ic_media_play
                )
            )

        }
    }
    private fun BarProress(){
        //essai bar progression

        var progressStatus = 0;

        val handler: Handler = Handler()

        Thread(Runnable {
            while (progressStatus < 100) {
                // Update the progress status
                progressStatus += 1
                // Try to sleep the thread for 50 milliseconds
                try {
                    Thread.sleep(50)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                // Update the progress bar
                handler.post(Runnable {
                    progressBar.progress = progressStatus
                })
            }
        }).start()
    }


}




