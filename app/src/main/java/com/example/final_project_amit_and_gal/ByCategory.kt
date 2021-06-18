package com.example.final_project_amit_and_gal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.final_project_amit_and_gal.cards_games.*

class ByCategory : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_by_category)
        buttonText()
        buttonOptions()
    }

    fun buttonText() {
        findViewById<Button>(R.id.game_find_dif).text = "יוצא דופן"
        findViewById<Button>(R.id.game_find_dif_by_cat).text = "מה יוצא דופן (קטוגריה)"
        findViewById<Button>(R.id.game_fix_order).text = "אותיות מבולגנות"
        findViewById<Button>(R.id.game_letter_choose).text = "זהה מילה מבנק המילים"
        findViewById<Button>(R.id.game_similar_category).text = "מה מאותה קטוגריה"
        findViewById<Button>(R.id.game_voice).text = "זיהוי דיבור"
        findViewById<Button>(R.id.game_what_in_pic).text = "מה בתמונה"
        findViewById<Button>(R.id.return_btn).text = "חזור"

    }
    fun buttonOptions() {
        var play_length = "10"
        findViewById<Button>(R.id.game_find_dif).setOnClickListener{
            val intent = Intent(this, find_the_diffrent::class.java)
            intent.putExtra("time", play_length)
            intent.putExtra("type", "0")
            intent.putExtra("score", "0")
            startActivity(intent)
        }
        findViewById<Button>(R.id.game_find_dif_by_cat).setOnClickListener{
            val intent = Intent(this, find_the_different_category::class.java)
            intent.putExtra("time", play_length)
            intent.putExtra("type", "0")
            intent.putExtra("score", "0")
            startActivity(intent)
        }
        findViewById<Button>(R.id.game_fix_order).setOnClickListener{
            val intent = Intent(this, fix_letter_order::class.java)
            intent.putExtra("time", play_length)
            intent.putExtra("type", "0")
            intent.putExtra("score", "0")
            startActivity(intent)
        }
        findViewById<Button>(R.id.game_letter_choose).setOnClickListener{
            val intent = Intent(this, find_the_diffrent::class.java)
            intent.putExtra("time", play_length)
            intent.putExtra("type", "0")
            intent.putExtra("score", "0")
            startActivity(intent)
        }
        findViewById<Button>(R.id.game_similar_category).setOnClickListener{
            val intent = Intent(this, similar_category::class.java)
            intent.putExtra("time", play_length)
            intent.putExtra("type", "0")
            intent.putExtra("score", "0")
            startActivity(intent)
        }
        findViewById<Button>(R.id.game_voice).setOnClickListener{
            val intent = Intent(this, find_the_diffrent::class.java)
            intent.putExtra("time", play_length)
            intent.putExtra("type", "0")
            intent.putExtra("score", "0")
            startActivity(intent)
        }
        findViewById<Button>(R.id.game_what_in_pic).setOnClickListener{
            val intent = Intent(this, whats_in_the_picture::class.java)
            intent.putExtra("time", play_length)
            intent.putExtra("type", "0")
            intent.putExtra("score", "0")
            startActivity(intent)
        }
        findViewById<Button>(R.id.return_btn).setOnClickListener{
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
        }

    }
}