package com.example.final_project_amit_and_gal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.final_project_amit_and_gal.cards_games.*

class ChooseExc : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_exc)
        val time:String = intent.getStringExtra("time").toString()


        val cards = findViewById<Button>(R.id.cards)
        cards.setOnClickListener {
            val intent = Intent(this, fix_letter_order::class.java)
            intent.putExtra("time", time)
            intent.putExtra("score","0")
            intent.putExtra("type",getIntent().getStringExtra("type"))
            
            startActivity(intent)
        }
        val face = findViewById<Button>(R.id.face)
        face.setOnClickListener {
            val intent = Intent(this, Face_Recognition::class.java)
            /*intent.putExtra("time", time)
            intent.putExtra("score","0")*/
            startActivity(intent)
        }
    }
}