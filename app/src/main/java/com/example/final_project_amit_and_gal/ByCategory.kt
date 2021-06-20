package com.example.final_project_amit_and_gal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.final_project_amit_and_gal.cards_games.*

class ByCategory : AppCompatActivity() {
    var play_length = "10"
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

        findViewById<Button>(R.id.game_find_dif).setOnClickListener{
            moveActivity("מצא את ההבדל", find_the_diffrent::class.java)
        }
        findViewById<Button>(R.id.game_find_dif_by_cat).setOnClickListener{
            moveActivity("מצא את ההבדל (קטגוריה)", find_the_different_category::class.java)
        }
        findViewById<Button>(R.id.game_fix_order).setOnClickListener{
            moveActivity("מילה מבולגנת",fix_letter_order::class.java)
        }
        findViewById<Button>(R.id.game_letter_choose).setOnClickListener{
            moveActivity("בחירת אותיות מבנק",letters_choose::class.java)
        }
        findViewById<Button>(R.id.game_similar_category).setOnClickListener{
            moveActivity("קטגוריה דומה", similar_category::class.java)
        }
        findViewById<Button>(R.id.game_voice).setOnClickListener{
            moveActivity("זיהוי קול",whats_in_the_picture::class.java)
        }
        findViewById<Button>(R.id.game_what_in_pic).setOnClickListener{
            moveActivity("מה בתמונה", whats_in_the_picture::class.java)
        }
        findViewById<Button>(R.id.return_btn).setOnClickListener{
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
        }

    }
    private fun moveActivity(name :String, game : Class<out AppCompatActivity>) {
        val intent = Intent(this, game)
        intent.putExtra("time", play_length)
        //למה צריך את הסקור באקטיביטי הזה ?
        intent.putExtra("score", "0")
        intent.putExtra("type", "0")
        intent.putExtra("name", name)
        startActivity(intent)
    }
}