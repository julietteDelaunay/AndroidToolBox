package fr.isen.delaunay.androidtoolbox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_form.*
import org.json.JSONObject
import java.io.File
import java.util.*

class FormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)


        buttonvalidation.setOnClickListener() {
            var NOM: String = editNAME.text.toString()
            var PRENOM : String = editPRENOM.text.toString()
            var DATE : String = editDATE.text.toString()
            val answer = JSONObject()
            val path: String = getFilesDir().getAbsolutePath().toString()+ "fichier.json"
            answer.put("nom",NOM)
            answer.put("prenom",PRENOM)
            answer.put("dateNaissance", DATE)

            val json = answer.toString()

            File(path).writeText(json)

            //retour a la page home
           // val homeIntent = Intent(this, HomeActivity::class.java)
           // startActivity(homeIntent)

        }
        buttonPOPUP.setOnClickListener() {
            val builder = AlertDialog.Builder(this@FormActivity)
            builder.setMessage("le nom:")
        }



        }
    }

