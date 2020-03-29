package fr.isen.delaunay.androidtoolbox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class BluetoothDetailAdapter : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bluetooth_detail_adapter_cell)
    }

}
/*
port com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

import android.bluetooth.BluetoothGattCharacteristic
import android.view.LayoutInflater
import android.view.ViewGroup
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder


class BLEServiceAdapter(private val serviceList : MutableList<BLEService>) :
    ExpandableRecyclerViewAdapter<BLEServiceAdapter.ServiceViewHolder, BLEServiceAdapter.CharacteristicViewHolder>(groups) {

    class ServiceViewHolder(itemView : View): GroupViewHolder(itemView){
        val textName = itemView.textName}

    class CharacteristicViewHolder(itemView : View): ChildViewHolder(itemView){}
    val textUUID = itemView.textUUID

    override fun onCreateGroupViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder = ServiceViewHolder{
        ServiceViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_genre,parent,false)
        )
    }

    override fun onCreateChildViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacteristicViewHolder= CharacteristicViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_artist,parent, false))



    override fun onBindChildViewHolder(
        holder: CharacteristicViewHolder, flatPosition: Int, group: ExpandableGroup<*>,
        childIndex: Int
    ) {
        val characteristic: BluetoothGattCharacteristic = (group as BLEService).items[childIndex]

        holder.characte
    }

    override fun onBindGroupViewHolder(
        holder: ServiceViewHolder, flatPosition: Int,
        group: ExpandableGroup<*>
    ) {
        val title = group.title
    }

}*/