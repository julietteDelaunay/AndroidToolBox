package fr.isen.delaunay.androidtoolbox

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //creation pref et editor
        val sharedPref: SharedPreferences = getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor= sharedPref.edit()
        setContentView(R.layout.activity_home)

            //si le bouton image ( cycle de vie) est activée
        imgCycleDeVie.setOnClickListener(){
                //pour demarer l'activitée CycleLife
                val intent = Intent(this, CycleLife::class.java)
                startActivity(intent)

        }
        buttonSTOP.setOnClickListener(){
            //vide editor
            editor.clear()
            //applique
            editor.apply()
            //reviens a login activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }
        imgSauvegarde.setOnClickListener() {
            val intent = Intent(this, FormActivity::class.java)
            startActivity(intent)
        }
        imgPermission.setOnClickListener() {
            val intent = Intent(this, PermissionActivity::class.java)
            startActivity(intent)
        }
        imageBluetooth.setOnClickListener() {
            val intent = Intent(this, BLEScanActivity::class.java)
            startActivity(intent)
        }
        imgWebServices.setOnClickListener() {
           val intent = Intent(this, WebServiceActivity::class.java)
         startActivity(intent)
        }

        }
    }

