package com.example.final_project_amit_and_gal

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Color.RED
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.final_project_amit_and_gal.cards_games.*

class FaceRecoMenu : AppCompatActivity() {
    var play_length : Int = 0
    var list_tasks : ArrayList<String> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_face_reco_menu)
        buttonText()
        buttonOptions()
    }

    fun buttonText() {
        findViewById<Button>(R.id.game_find_dif).text = getString(R.string.face_1)
        findViewById<Button>(R.id.game_find_dif_by_cat).text = getString(R.string.face_2)
        findViewById<Button>(R.id.game_fix_order).text = getString(R.string.face_3)
        findViewById<Button>(R.id.game_letter_choose).text = getString(R.string.face_4)
        findViewById<Button>(R.id.game_similar_category).text = getString(R.string.face_5)
        findViewById<Button>(R.id.game_voice).text = getString(R.string.face_6)
        findViewById<Button>(R.id.game_what_in_pic).text = getString(R.string.face_7)
        findViewById<Button>(R.id.return_btn).text = getString(R.string.face_8)

    }
    fun buttonEyes(type:String){

    }
    fun buttonJaw(type:String){

    }
    fun buttonTongue(type:String){

    }
    fun buttonLips(type:String){

    }
    fun buttonOptions() {
        findViewById<Button>(R.id.game_find_dif).setOnClickListener{ b ->
            if(list_tasks.contains(getString(R.string.face_1))){
                b.setBackground(getDrawable(R.drawable.category_custom_bottom))


                list_tasks.remove(getString(R.string.face_1))
            } else {
                b.setBackground(getDrawable(R.drawable.delete_bottom))

                list_tasks.add(getString(R.string.face_1))
            }

           // moveActivity(getString(R.string.face_1), Face_Recognition::class.java)
        }
        findViewById<Button>(R.id.game_find_dif_by_cat).setOnClickListener{
                b ->
            if(list_tasks.contains(getString(R.string.face_2))){
                b.setBackground(getDrawable(R.drawable.category_custom_bottom))


                list_tasks.remove(getString(R.string.face_2))
            } else {
                b.setBackground(getDrawable(R.drawable.delete_bottom))


                list_tasks.add(getString(R.string.face_2))
            }

           // moveActivity(getString(R.string.face_2), Face_Recognition::class.java)
        }
        findViewById<Button>(R.id.game_fix_order).setOnClickListener{
                b ->
            if(list_tasks.contains(getString(R.string.face_3))){
                b.setBackground(getDrawable(R.drawable.category_custom_bottom))


                list_tasks.remove(getString(R.string.face_3))
            } else {
                b.setBackground(getDrawable(R.drawable.delete_bottom))


                list_tasks.add(getString(R.string.face_3))
            }
            //moveActivity(getString(R.string.face_3), Face_Recognition::class.java)
        }
        findViewById<Button>(R.id.game_letter_choose).setOnClickListener{
                b ->
            if(list_tasks.contains(getString(R.string.face_4))){
                b.setBackground(getDrawable(R.drawable.category_custom_bottom))


                list_tasks.remove(getString(R.string.face_4))
            } else {
                b.setBackground(getDrawable(R.drawable.delete_bottom))


                list_tasks.add(getString(R.string.face_4))
            }
        }
        findViewById<Button>(R.id.game_similar_category).setOnClickListener{
                b ->
            if(list_tasks.contains(getString(R.string.face_5))){
                b.setBackground(getDrawable(R.drawable.category_custom_bottom))


                list_tasks.remove(getString(R.string.face_5))
            } else {
                b.setBackground(getDrawable(R.drawable.delete_bottom))


                list_tasks.add(getString(R.string.face_5))
            }
        }
        findViewById<Button>(R.id.game_voice).setOnClickListener{
                b ->
            if(list_tasks.contains(getString(R.string.face_6))){
                b.setBackground(getDrawable(R.drawable.category_custom_bottom))


                list_tasks.remove(getString(R.string.face_6))
            } else {
                b.setBackground(getDrawable(R.drawable.delete_bottom))


                list_tasks.add(getString(R.string.face_6))
            }
        }
        findViewById<Button>(R.id.game_what_in_pic).setOnClickListener{
                b ->
            if(list_tasks.contains(getString(R.string.face_7))){
                b.setBackground(getDrawable(R.drawable.category_custom_bottom))


                list_tasks.remove(getString(R.string.face_7))
            } else {
                b.setBackground(getDrawable(R.drawable.delete_bottom))


                list_tasks.add(getString(R.string.face_7))
            }
        }
        findViewById<Button>(R.id.return_btn).setOnClickListener{
                b ->
            if(list_tasks.contains(getString(R.string.face_8))){
                b.setBackground(getDrawable(R.drawable.category_custom_bottom))



                list_tasks.remove(getString(R.string.face_8))
            } else {
                b.setBackground(getDrawable(R.drawable.delete_bottom))


                list_tasks.add(getString(R.string.face_8))
            }
        }
        findViewById<Button>(R.id.start_tasks).setOnClickListener{
            moveActivity(list_tasks, Face_Recognition::class.java)
        }

    }
    private fun moveActivity(name :ArrayList<String>, game : Class<out AppCompatActivity>) {
        val intent = Intent(this, game)
        Log.i("tag", name.toString())
        intent.putStringArrayListExtra("name", name)
        startActivity(intent)
    }
}