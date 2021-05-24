package com.example.final_project_amit_and_gal.cards_games

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.final_project_amit_and_gal.R

class specific_game : game_concept() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specific_game)

    }
}