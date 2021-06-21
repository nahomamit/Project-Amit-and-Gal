package com.example.final_project_amit_and_gal

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        setupPermissions()
        val sp1 = getSharedPreferences("Login", MODE_PRIVATE)

        val name_text = sp1.getString("Name", null)

        findViewById<TextView>(R.id.hello_name).text = "שלום " + name_text
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
            val intent = Intent(this, WeeklySched::class.java)
            startActivity(intent)
        }
    }
    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i("TAG", "Permission to record denied")
        }
        makeRequest()
    }
    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
            101)
    }
}