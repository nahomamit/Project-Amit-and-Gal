package com.example.final_project_amit_and_gal

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.final_project_amit_and_gal.cards_games.*

class FaceTypeMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_face_type_menu)
        buttonText()
        buttonOptions()
    }
    fun buttonText() {
        findViewById<Button>(R.id.game_find_dif).text = getString(R.string.face_type1)
        findViewById<Button>(R.id.game_find_dif_by_cat).text = getString(R.string.face_type2)
        findViewById<Button>(R.id.game_fix_order).text = getString(R.string.face_type3)
        findViewById<Button>(R.id.game_letter_choose).text = getString(R.string.face_type4)


    }
    fun buttonOptions() {

        findViewById<Button>(R.id.game_find_dif).setOnClickListener{
            moveActivity(getString(R.string.face_type1), FaceRecoMenu::class.java)
        }
        findViewById<Button>(R.id.game_find_dif_by_cat).setOnClickListener{
            moveActivity(getString(R.string.face_type2), FaceRecoMenu::class.java)
        }
        findViewById<Button>(R.id.game_fix_order).setOnClickListener{
            moveActivity(getString(R.string.face_type3), FaceRecoMenu::class.java)
        }
        findViewById<Button>(R.id.game_letter_choose).setOnClickListener{
            moveActivity(getString(R.string.face_type4), FaceRecoMenu::class.java)
        }

    }
    private fun moveActivity(name :String, game : Class<out AppCompatActivity>) {
        val intent = Intent(this, game)

        //למה צריך את הסקור באקטיביטי הזה ?
        intent.putExtra("type", name)
        startActivity(intent)
    }
}