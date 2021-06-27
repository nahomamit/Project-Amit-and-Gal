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


        val button_short = findViewById<Button>(R.id.short_time)
        button_short.setOnClickListener {
            moveActivity("20",getString(R.string.game_short))

        }
        val button_med = findViewById<Button>(R.id.mid_time)
        button_med.setOnClickListener {
            moveActivity("40", getString(R.string.game_med))

        }
        val button_long = findViewById<Button>(R.id.long_time)
        button_long.setOnClickListener {
            moveActivity("60", getString(R.string.game_long))

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

}