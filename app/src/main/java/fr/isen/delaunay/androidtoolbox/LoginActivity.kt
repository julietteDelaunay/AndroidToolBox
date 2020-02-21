package fr.isen.delaunay.androidtoolbox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        buttonvalidate.setOnClickListener(){
            var idetifiantUser :String = editID.text.toString()
            var passwordUser : String = editPSW.text.toString()
            //q d
            //Toast.makeText(applicationContext,"votre ID est: ${editID.text.toString()}",Toast.LENGTH_SHORT).show()
            //question e
            if (idetifiantUser == "admin"&& passwordUser== "123")
            {
                //pour demarer l'activitée HomeActivity
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                //dire que l'ID et le pswd est valide
            //Toast.makeText(applicationContext,"votre ID est: ${idetifiantUser}",Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(applicationContext,"votre ID ou votre password est mauvais ",Toast.LENGTH_SHORT).show()
            }
        }


    }

}
