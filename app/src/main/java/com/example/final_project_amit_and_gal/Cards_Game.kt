package com.example.final_project_amit_and_gal

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import java.util.*
import kotlin.math.log
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt

class Cards_Game : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cards__game)
        val time:String = intent.getStringExtra("time").toString()
        val text = findViewById<TextView>(R.id.task_for_costumer)
        val current_exercize = randomExcercize()
        text.text = current_exercize
        val back = findViewById<ImageView>(R.id.return_btn)
        back.setOnClickListener() {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("האם אתה מעוניין לסיים את התרגול ולחזור לתפריט הראשי?")
                .setCancelable(false)
                .setPositiveButton("כן") { dialogInterface: DialogInterface, i: Int ->
                    val intent = Intent(this, MainActivity::class.java)
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
    fun randomExcercize(): String {
        val exc_arr = listOf("מה בתמונה?", "בחר ביוצא הדופן", "תגיד בקול מה בתמונה", "בחר ביוצא הדופן 2")
        val chosen = exc_arr.random()
        return chosen
    }

}