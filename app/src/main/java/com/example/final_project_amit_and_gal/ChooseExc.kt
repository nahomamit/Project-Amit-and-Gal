package com.example.final_project_amit_and_gal

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.final_project_amit_and_gal.cards_games.find_the_diffrent
import com.example.final_project_amit_and_gal.cards_games.whats_in_the_picture

class ChooseExc :SharedFunctions() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_exc)
        val time:String = intent.getStringExtra("time").toString()


        val cards = findViewById<Button>(R.id.cards)
        cards.setOnClickListener {
            val intent = Intent(this, whats_in_the_picture::class.java)
            intent.putExtra("time", time)
            intent.putExtra("score","0")
            intent.putExtra("type",getIntent().getStringExtra("type"))
            intent.putExtra("name",getIntent().getStringExtra("name"))

            startActivity(intent)
        }
        val face = findViewById<Button>(R.id.face)
        face.setOnClickListener {
            var permission = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CAMERA)

            if (permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.CAMERA),
                    101)
            }

            permission = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CAMERA)
            if (permission != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "getString(R.string.no_permission)", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, Face_Recognition::class.java)
                /*intent.putExtra("time", time)
                intent.putExtra("score","0")*/
                startActivity(intent)
            }

        }
    }
}