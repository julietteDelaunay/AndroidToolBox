package fr.isen.delaunay.androidtoolbox

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import fr.isen.delaunay.androidtoolbox.Web_service_Result.User
import kotlinx.android.synthetic.main.web_service_cell.view.*


class WebServiceAdapter(private val users: User, val context: Context) :
    RecyclerView.Adapter<WebServiceAdapter.WebHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.web_service_cell, parent, false)
        return WebHolder(users, view, context)
    }

    override fun getItemCount(): Int {
        return users.results.size
    }

    override fun onBindViewHolder(holder: WebHolder, position: Int) {
        holder.loadInfo(position)
    }

    class WebHolder(private val webUsers: User, view: View, val context: Context) :
        RecyclerView.ViewHolder(view) {
        private val name: TextView = view.nameWS
        private val image: ImageView = view.pictWS
        private val address: TextView = view.addressWS
        private val email: TextView = view.mailWS


        fun loadInfo(index: Int) {
            val nameWebServ =
                webUsers.results[index].name.first + " " + webUsers.results[index].name.last
            val addressWebServ =
                webUsers.results[index].location.street.number.toString() + " " + webUsers.results[index].location.street.name + " " + webUsers.results[index].location.city
            Glide.with(context)
                .load(webUsers.results[index].picture.large)
                .apply(RequestOptions.circleCropTransform())
                .into(image)

            name.text = nameWebServ
            email.text = webUsers.results[index].email
            address.text = addressWebServ
        }
    }
}
