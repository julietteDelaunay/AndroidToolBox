package fr.isen.delaunay.androidtoolbox

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*



class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //creation sharedPref et editor 
        val sharedPref: SharedPreferences = getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor = sharedPref.edit()

        if (sharedPref.getString("ID","")=="admin" && sharedPref.getString("PSW","")=="123") {
            val homeIntent = Intent(this, HomeActivity::class.java)
            startActivity(homeIntent)
            finish()
            Toast.makeText(this, "login dejà enregistré", Toast.LENGTH_SHORT).show();

        }
        else{
                buttonvalidate.setOnClickListener() {
                var idetifiantUser: String = editID.text.toString()
                var passwordUser: String = editPSW.text.toString()
                //q d
                //Toast.makeText(applicationContext,"votre ID est: ${editID.text.toString()}",Toast.LENGTH_SHORT).show()
                //question e
                    if (idetifiantUser == "admin"&& passwordUser== "123") {
                        editor.putString("ID", idetifiantUser)
                        editor.putString("PSW", passwordUser)
                        editor.apply()
                        val homeIntent = Intent(this, FormActivity::class.java)
                        startActivity(homeIntent)
                        finish()

                    }
                     else
                     {
                         Toast.makeText(applicationContext,"votre ID ou votre password est mauvais ",Toast.LENGTH_SHORT).show()
                     }
                }
        }




    }

}
