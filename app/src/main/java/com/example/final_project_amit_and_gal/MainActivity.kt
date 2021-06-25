package com.example.final_project_amit_and_gal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.final_project_amit_and_gal.cards_games.*
import kotlin.random.Random


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
        val intent = Intent(this, randomActivity())
        intent.putExtra("time", time)
        intent.putExtra("score","0")
        intent.putExtra("type",getIntent().getStringExtra("type"))
        intent.putExtra("name",name)

        startActivity(intent)
    }

    private fun randomActivity(): Class<*>? {
        val number = (1..7).random()

        var activity: Class<*>? = null

        // Here, we are checking to see what the output of the random was
        activity = when (number) {
            1 -> find_the_different_category::class.java
            2 -> find_the_diffrent::class.java
            3 -> fix_letter_order::class.java
            4 -> letters_choose::class.java
            5 -> similar_category::class.java
            6 -> VoiceReco::class.java
            else -> whats_in_the_picture::class.java
        }
        return activity
    }


}