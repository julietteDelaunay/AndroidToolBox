package fr.isen.delaunay.androidtoolbox

import android.app.DatePickerDialog
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
    var age = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)


        buttonValidate.setOnClickListener() {
            var NOM: String = editNAME.text.toString()
            var PRENOM: String = editPRENOM.text.toString()
            var DATE: String = buttonEdite.text.toString()
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


        val cal = Calendar.getInstance()
        val dateSetListener =
            DatePickerDialog.OnDateSetListener{ datePicker : DatePicker, year : Int, monthOfYear : Int, dayOfMonth : Int ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val sdf = SimpleDateFormat("dd/MM/yyyy")
                buttonEdite.text = sdf.format(cal.time)

                val today = Calendar.getInstance()

                age = today.get(Calendar.YEAR) - cal.get(Calendar.YEAR)

                if (today.get(Calendar.DAY_OF_YEAR) < cal.get(Calendar.DAY_OF_YEAR))
                    age--
            }

        fun showDatePicker(dateSetListener:DatePickerDialog.OnDateSetListener){
            val cal = Calendar.getInstance()
            DatePickerDialog(this, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        buttonEdite.setOnClickListener {
            showDatePicker(dateSetListener)
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
                ).toString() + "\n" + "Date de Naissance: " + json.get("dateNaissance").toString() + "\n"+ "Age : +$age +ans" + "\n"
            )
            // Finally, make the alert dialog using builder
            val dialog: AlertDialog = alertDialogBuilder.create()
            // Display the alert dialog on app interface
            dialog.show()

        }



    }
}
