package com.example.final_project_amit_and_gal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button_short = findViewById<Button>(R.id.short_time)
        button_short.setOnClickListener {
            val intent = Intent(this, ChooseExc::class.java)
            intent.putExtra("time","0")
            startActivity(intent)
        }
        val button_med = findViewById<Button>(R.id.mid_time)
        button_med.setOnClickListener {
            val intent = Intent(this, ChooseExc::class.java)
            intent.putExtra("time","1")
            startActivity(intent)
        }
        val button_long = findViewById<Button>(R.id.long_time)
        button_long.setOnClickListener {
            val intent = Intent(this, ChooseExc::class.java)
            intent.putExtra("time","2")
            startActivity(intent)
        }
    }
}