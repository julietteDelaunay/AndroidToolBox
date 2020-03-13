package fr.isen.delaunay.androidtoolbox

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_form.*
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class FormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)


        buttonvalidation.setOnClickListener() {
            var NOM: String = editNAME.text.toString()
            var PRENOM: String = editPRENOM.text.toString()
            var DATE: String = editDATE.text.toString()
            val answer = JSONObject()
            val path: String = getFilesDir().getAbsolutePath().toString() + "fichier.json"
            //val path : String = cacheDir.absolutePath+ "fichier.json"
            answer.put("nom", NOM)
            answer.put("prenom", PRENOM)
            answer.put("dateNaissance", DATE)

            val json = answer.toString()

            File(path).writeText(json)

            //retour a la page home
            // val homeIntent = Intent(this, HomeActivity::class.java)
            // startActivity(homeIntent)

        }
        buttonPOPUP.setOnClickListener() {
            val builder = AlertDialog.Builder(this@FormActivity)
            // Set the alert dialog title
            //val data :String = File(cacheDir.absolutePath, "fichier.json" ).readText()
            val result =
                File(getFilesDir().getAbsolutePath().toString() + "fichier.json").readText(Charsets.UTF_8)
            val json = JSONObject(result)
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("profil utilisateur : ")
            alertDialogBuilder.setMessage(
                "Nom: " + json.get("nom").toString() + "\n" + "Prénom: " + json.get(
                    "prenom"
                ).toString() + "\n" + "Date de Naissance: " + json.get("dateNaissance").toString() + "\n"
            )
            // Finally, make the alert dialog using builder
            val dialog: AlertDialog = alertDialogBuilder.create()
            // Display the alert dialog on app interface
            dialog.show()


        }



    }
}

