package com.example.final_project_amit_and_gal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        val button_short = findViewById<Button>(R.id.random_play)
        button_short.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val button_med = findViewById<Button>(R.id.by_category)
        button_med.setOnClickListener {
            val intent = Intent(this, ByCategory::class.java)
            startActivity(intent)
        }
        val button_long = findViewById<Button>(R.id.weekly_practice)
        button_long.setOnClickListener {

        }
    }
}