package fr.isen.delaunay.androidtoolbox

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.delaunay.androidtoolbox.Web_service_Result.User
import kotlinx.android.synthetic.main.activity_web_service.*
class WebServiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_service)
        Api()
    }
    private fun Api(): User {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        var user = User()
        val stringRequest = JsonObjectRequest(
            Request.Method.GET,
            "https://randomuser.me/api/?inc=name%2Cpicture%2Clocation%2Cemail&noinfo=&nat=fr&format=pretty&results=30",
            null,
            Response.Listener { response ->
                val gson = Gson()
                user = gson.fromJson(response.toString(), User::class.java)
                UserRV.layoutManager = LinearLayoutManager(this)
                UserRV.adapter = WebServiceAdapter(user,this)
                UserRV.visibility = View.VISIBLE
            },
            Response.ErrorListener {
                Toast.makeText(this, "Uh oh, something has gone wrong.", Toast.LENGTH_SHORT).show()
            }
        )
        // Add the request to the RequestQueue.
        queue.add(stringRequest)
        return user
    }
}