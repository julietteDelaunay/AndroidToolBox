package fr.isen.delaunay.androidtoolbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cycle_life.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.textCycle

class CycleLife : AppCompatActivity() {
var texte :String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cycle_life)
        texte += "app create\n"
        textCycle.text= texte
    }

    override fun onStart() {
        super.onStart()
        texte += "app start\n"
        textCycle.text= texte
    }
    override fun onPause() {
        super.onPause()
        texte += "app pause\n"
        textCycle.text= texte
        Toast.makeText(applicationContext,"app pause",Toast.LENGTH_SHORT).show()

    }

    override fun onResume() {
        super.onResume()
        texte += "app resume\n"
        textCycle.text= texte
        Toast.makeText(applicationContext,"app Resume",Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        texte += "app stop\n"
        textCycle.text= texte
        Toast.makeText(applicationContext,"app stop",Toast.LENGTH_SHORT).show()
        Log.i("stop" ,  "en stop")

    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(applicationContext,"app Destroy",Toast.LENGTH_SHORT).show()
        Log.i("destroy" ,  "Destroy")
    }
}
