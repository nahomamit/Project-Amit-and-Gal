package com.example.final_project_amit_and_gal.cards_games

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.final_project_amit_and_gal.R

class whats_in_the_picture : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_whats_in_the_picture)
        val time:String = intent.getStringExtra("time").toString()
        val text = findViewById<TextView>(R.id.task_for_costumer)
        text.text = "מה בתמונה?"
        val current_exercize = nextExcercize()
        //text.text = current_exercize
    }
    fun nextExcercize(): String {
        val exc_arr = listOf("מה בתמונה?", "בחר ביוצא הדופן", "תגיד בקול מה בתמונה", "בחר ביוצא הדופן 2")
        val chosen = exc_arr.random()
        return chosen
    }
}