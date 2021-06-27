package com.example.final_project_amit_and_gal

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.final_project_amit_and_gal.cards_games.*

class FaceTypeMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_face_type_menu)
        buttonText()
        buttonOptions()
        val back = findViewById<ImageView>(R.id.return_btn)
        back.setOnClickListener{back_btn()}
    }
    fun back_btn(){

        val builder = AlertDialog.Builder(this)
        builder.setMessage("האם אתה מעוניין לסיים את התרגול ולחזור לתפריט הראשי?")
            .setCancelable(false)
            .setPositiveButton("כן") { dialogInterface: DialogInterface, i: Int ->
                val intent = Intent(this, MainMenu::class.java)
                startActivity(intent)
            }
            .setNegativeButton("לא") { dialog, id ->
                // Dismiss the dialog
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    fun buttonText() {
        findViewById<Button>(R.id.type_eyes).text = getString(R.string.face_eyes)
        findViewById<Button>(R.id.type_jaw).text = getString(R.string.face_jaw)
        findViewById<Button>(R.id.type_lips).text = getString(R.string.face_lips)
        findViewById<Button>(R.id.type_tongue).text = getString(R.string.face_tongue)


    }
    fun buttonOptions() {

        findViewById<Button>(R.id.type_eyes).setOnClickListener{
            moveActivity(getString(R.string.face_eyes), FaceRecoMenu::class.java)
        }
        findViewById<Button>(R.id.type_jaw).setOnClickListener{
            moveActivity(getString(R.string.face_jaw), FaceRecoMenu::class.java)
        }
        findViewById<Button>(R.id.type_lips).setOnClickListener{
            moveActivity(getString(R.string.face_lips), FaceRecoMenu::class.java)
        }
        findViewById<Button>(R.id.type_tongue).setOnClickListener{
            moveActivity(getString(R.string.face_tongue), FaceRecoMenu::class.java)
        }

    }
    private fun moveActivity(name :String, game : Class<out AppCompatActivity>) {
        val intent = Intent(this, game)

        //למה צריך את הסקור באקטיביטי הזה ?
        intent.putExtra("type", name)
        startActivity(intent)
    }
}