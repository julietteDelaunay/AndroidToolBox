package fr.isen.delaunay.androidtoolbox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_login.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

            //si le bouton image ( cycle de vie) est activée
        imgCycleDeVie.setOnClickListener(){
                //pour demarer l'activitée CycleLife
                val intent = Intent(this, CycleLife::class.java)
                startActivity(intent)

        }
    }
}
