package com.example.final_project_amit_and_gal

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

open class SharedFunctions: AppCompatActivity() {

    override fun onBackPressed(){
        back_btn()

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
}