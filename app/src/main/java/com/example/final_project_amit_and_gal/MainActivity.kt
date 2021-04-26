package com.example.final_project_amit_and_gal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.short_time)
        button.setOnClickListener {
            val intent = Intent(this, ChooseExc::class.java)
            startActivity(intent)
        }
    }


}