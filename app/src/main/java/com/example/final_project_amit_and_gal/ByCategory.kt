package com.example.final_project_amit_and_gal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.final_project_amit_and_gal.cards_games.*

class ByCategory : SharedFunctions() {
    var play_length = "10"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_by_category)
        buttonText()
        buttonOptions()
    }

    fun buttonText() {
        findViewById<Button>(R.id.game_find_dif).text = getString(R.string.game_find_dif)
        findViewById<Button>(R.id.game_find_dif_by_cat).text = getString(R.string.game_find_dif_by_cat)
        findViewById<Button>(R.id.game_fix_order).text = getString(R.string.game_fix_letter_order)
        findViewById<Button>(R.id.game_letter_choose).text = getString(R.string.game_letter_choose)
        findViewById<Button>(R.id.game_similar_category).text = getString(R.string.game_similar_category)
        findViewById<Button>(R.id.game_voice).text = getString(R.string.game_voice_reco)
        findViewById<Button>(R.id.game_what_in_pic).text = getString(R.string.game_whats_in_the_pic)
        findViewById<Button>(R.id.return_btn).text = getString(R.string.back)

    }
    fun buttonOptions() {

        findViewById<Button>(R.id.game_find_dif).setOnClickListener{
            moveActivity(getString(R.string.game_find_dif), find_the_diffrent::class.java)
        }
        findViewById<Button>(R.id.game_find_dif_by_cat).setOnClickListener{
            moveActivity(getString(R.string.game_find_dif_by_cat), find_the_different_category::class.java)
        }
        findViewById<Button>(R.id.game_fix_order).setOnClickListener{
            moveActivity(getString(R.string.game_fix_letter_order),fix_letter_order::class.java)
        }
        findViewById<Button>(R.id.game_letter_choose).setOnClickListener{
            moveActivity(getString(R.string.game_letter_choose),letters_choose::class.java)
        }
        findViewById<Button>(R.id.game_similar_category).setOnClickListener{
            moveActivity(getString(R.string.game_similar_category), similar_category::class.java)
        }
        findViewById<Button>(R.id.game_voice).setOnClickListener{
            moveActivity(getString(R.string.game_voice_reco),VoiceReco::class.java)
        }
        findViewById<Button>(R.id.game_what_in_pic).setOnClickListener{
            moveActivity(getString(R.string.game_whats_in_the_pic), whats_in_the_picture::class.java)
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