package fr.isen.delaunay.androidtoolbox

import android.app.AlertDialog
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder
import kotlinx.android.synthetic.main.bluetooth_characteristique_cell.view.*
import kotlinx.android.synthetic.main.bluetooth_detail_adapter_cell.view.*
import kotlinx.android.synthetic.main.bluetooth_dialog.view.*
import java.util.*


class BluetoothDetailAdapter(serviceList: MutableList<BLEService>, var context: Context, gatt: BluetoothGatt?):
    ExpandableRecyclerViewAdapter<BluetoothDetailAdapter.ServicesViewHolder, BluetoothDetailAdapter.CharacteristicViewHolder>(
        serviceList
    ) {

    val ble: BluetoothGatt? = gatt
    var notifier = false

    class ServicesViewHolder(detailsView: View) : GroupViewHolder(detailsView) {
        val arrow: ImageView = detailsView.arrow

        val serviceUuid: TextView = detailsView.txtuuid
        val nameService: TextView = detailsView.txtserviceName


        override fun expand() {
            animateExpand()
        }

        override fun collapse() {
            animateCollapse()
        }

        private fun animateExpand() {
            val rotate = RotateAnimation(
                360F,
                180F,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            )
            rotate.duration = 300
            rotate.fillAfter = true
            arrow.animation = rotate
        }

        private fun animateCollapse() {
            val rotate = RotateAnimation(
                180F,
                360F,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            )
            rotate.duration = 300
            rotate.fillAfter = true
            arrow.animation = rotate
        }
    }

    class CharacteristicViewHolder(itemView: View) : ChildViewHolder(itemView) {
        val name: TextView = itemView.txtName
        val uuid: TextView = itemView.txtUUID
        val value: TextView = itemView.txtValue
        val properties: TextView = itemView.txtProprieté
        val write: TextView = itemView.Button_write
        val read: TextView = itemView.Button_read
        val notify: TextView = itemView.Button_notify

    }

    override fun onCreateGroupViewHolder(parent: ViewGroup, viewType: Int): ServicesViewHolder =
        ServicesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.bluetooth_detail_adapter_cell,
                parent,
                false
            )
        )

    override fun onCreateChildViewHolder(
        parent: ViewGroup?,
        viewType: Int
    ): CharacteristicViewHolder =
        CharacteristicViewHolder(
            LayoutInflater.from(parent?.context)
                .inflate(R.layout.bluetooth_characteristique_cell, parent, false)
        )

    override fun onBindChildViewHolder(
        holder: CharacteristicViewHolder,
        flatPosition: Int,
        group: ExpandableGroup<*>,
        childIndex: Int
    ) {
        val characteristic: BluetoothGattCharacteristic =
            (group as BLEService).items[childIndex]

        val uuid = characteristic.uuid

        holder.uuid.text = uuid.toString()

        holder.value.text = ""

        if (characteristic.uuid == UUID.fromString("466c9abc-f593-11e8-8eb2-f2801f1b9fd1") && notifier) {
            holder.value.text = "${byteArrayToHexString(characteristic.value)}"
        } else if (characteristic.value != null) {
            holder.value.text = "${String(characteristic.value)}"
        } else {
            holder.value.text = ""
        }

        holder.read.setOnClickListener {
            ble?.readCharacteristic(characteristic)
        }


        holder.write.setOnClickListener {

            val dialog = AlertDialog.Builder(context)
            val editView = View.inflate(context, R.layout.bluetooth_dialog, null)

            dialog.setView(editView)
            dialog.setNegativeButton(
                "Annuler",
                DialogInterface.OnClickListener { dialog, which -> })
            dialog.setPositiveButton("Valider", DialogInterface.OnClickListener { _, _ ->
                val text = editView.value.text.toString()
                characteristic.setValue(text)
                ble?.writeCharacteristic(characteristic)
            })
            dialog.show()
        }


        holder.notify.setOnClickListener {
            if (!notifier) {
                notifier = true
                if (ble != null) {
                    setCharacteristicNotificationInternal(ble, characteristic, true)
                    if (characteristic.value != null) {
                        holder.value.text = byteArrayToHexString(characteristic.value)
                    } else {
                        holder.value.text = ""
                    }
                }
            } else {
                notifier = false
                if (ble != null) {
                    setCharacteristicNotificationInternal(ble, characteristic, false)
                }
            }
        }
    }

    override fun onBindGroupViewHolder(
        /*
        holder: ServicesViewHolder,
        flatPosition: Int,
        group: ExpandableGroup<*>
    ) {
        val title = Attributes.getAttribute(group.title).title
        holder.serviceUuid.text = group.title
        holder.service.text = title
         */

    holder: ServicesViewHolder,
    flatPosition: Int,
    group: ExpandableGroup<*>
    ) {
        val title = group.title
        val uuidName: String = when (group.title) {
            "00001800-0000-1000-8000-00805f9b34fb" -> "Accès générique"
            "00001801-0000-1000-8000-00805f9b34fb" -> "Attirbut générique"
            else -> "Service spécifique"
        }

        holder.serviceUuid.text = title
        holder.nameService.text = uuidName
    }


    private fun byteArrayToHexString(array: ByteArray): String {
        val result = StringBuilder(array.size * 2)
        for (byte in array) {
            val toAppend = String.format("%X", byte) // hexadecimal
            result.append(toAppend).append("-")
        }
        result.setLength(result.length - 1) // remove last '-'
        return result.toString()
    }

    private fun setCharacteristicNotificationInternal(
        gatt: BluetoothGatt,
        characteristic: BluetoothGattCharacteristic,
        enabled: Boolean
    ) {
        gatt.setCharacteristicNotification(characteristic, enabled)

        if (characteristic.descriptors.size > 0) {

            val descriptors = characteristic.descriptors
            for (descriptor in descriptors) {

                if (characteristic.properties and BluetoothGattCharacteristic.PROPERTY_NOTIFY != 0) {
                    descriptor.value =
                        if (enabled) BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE else BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE
                } else if (characteristic.properties and BluetoothGattCharacteristic.PROPERTY_INDICATE != 0) {
                    descriptor.value =
                        if (enabled) BluetoothGattDescriptor.ENABLE_INDICATION_VALUE else BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE
                }
                gatt.writeDescriptor(descriptor)
            }
        }
    }
}

