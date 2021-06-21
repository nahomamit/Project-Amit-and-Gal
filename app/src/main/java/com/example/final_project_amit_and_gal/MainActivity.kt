package com.example.final_project_amit_and_gal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader


class MainActivity : SharedFunctions() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var name = findViewById<TextView>(R.id.hello_name)
        val sp1 = getSharedPreferences("Login", MODE_PRIVATE)

        val name_text = sp1.getString("Name", null)

        name.text = "שלום " + name_text
        val button_short = findViewById<Button>(R.id.short_time)
        button_short.setOnClickListener {
            moveActivity("20", "תרגול רץ קצר")

        }
        val button_med = findViewById<Button>(R.id.mid_time)
        button_med.setOnClickListener {
            moveActivity("40", "תרגול רץ בינוני")

        }
        val button_long = findViewById<Button>(R.id.long_time)
        button_long.setOnClickListener {
            moveActivity("60", "תרגול רץ ארוך")

        }
    }

    private fun moveActivity(time: String, name :String) {
        val intent = Intent(this, ChooseExc::class.java)
        intent.putExtra("time", time)
        //למה צריך את הסקור באקטיביטי הזה ?
        intent.putExtra("score", "0")
        intent.putExtra("type", "1")
        intent.putExtra("name", name)
        startActivity(intent)
    }


}