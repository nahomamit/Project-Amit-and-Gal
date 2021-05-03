package com.example.final_project_amit_and_gal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Face_Reco : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_face__reco)

        val time:String = intent.getStringExtra("time").toString()
        val text = findViewById<TextView>(R.id.time_show)
        text.text = time
    }
}