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
import java.util.*
import kotlin.collections.ArrayList

class FaceRecoMenu : AppCompatActivity() {
    var play_length : Int = 0
    var list_tasks : ArrayList<String> = arrayListOf()
    var list_all_Tasks :ArrayList<String> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_face_reco_menu)
        buttonText()
        buttonOptions()
        var type = intent.getStringExtra("type")
        buildArr(type)
    }
    fun  buildArr(type:String){
        when(type){
            getString(R.string.face_jaw) -> list_all_Tasks.addAll(
                Arrays.asList(
                        getString (R.string.jaw_1),
                getString(R.string.jaw_2),
                getString(R.string.jaw_3),
                getString(R.string.jaw_4),
                getString(R.string.jaw_5),
                getString(R.string.jaw_6),
                getString(R.string.jaw_7),
                getString(R.string.jaw_8)
                )
            )
            getString(R.string.face_lips) -> list_all_Tasks.addAll(
                Arrays.asList(
                    getString (R.string.lips_1),
                    getString (R.string.lips_2),
                    getString (R.string.lips_3),
                    getString (R.string.lips_4),
                    getString (R.string.lips_5),
                    getString (R.string.lips_6),
                    getString (R.string.lips_7),
                    getString (R.string.lips_8)
                )
            )
            getString(R.string.face_tongue) -> list_all_Tasks.addAll(
                Arrays.asList(
                    getString (R.string.tongue_1),
                    getString (R.string.tongue_2),
                    getString (R.string.tongue_3),
                    getString (R.string.tongue_4),
                    getString (R.string.tongue_5),
                    getString (R.string.tongue_6),
                    getString (R.string.tongue_7),
                    getString (R.string.tongue_8)
                    )
            )
            getString(R.string.face_eyes) -> list_all_Tasks.addAll(
                Arrays.asList(
                    getString (R.string.eyes_1),
                    getString (R.string.eyes_2),
                    getString (R.string.eyes_3),
                    getString (R.string.eyes_4),
                    getString (R.string.eyes_5),
                    getString (R.string.eyes_6),
                    getString (R.string.eyes_7),
                    getString (R.string.eyes_8)

                )
            )
        }


    }

    fun buttonText() {

        findViewById<Button>(R.id.game_find_dif).text = list_all_Tasks[0]
        findViewById<Button>(R.id.game_find_dif_by_cat).text = list_all_Tasks[1]
        findViewById<Button>(R.id.game_fix_order).text = list_all_Tasks[2]
        findViewById<Button>(R.id.game_letter_choose).text = list_all_Tasks[3]
        findViewById<Button>(R.id.game_similar_category).text = list_all_Tasks[4]
        findViewById<Button>(R.id.game_voice).text = list_all_Tasks[5]
        findViewById<Button>(R.id.game_what_in_pic).text = list_all_Tasks[6]
        findViewById<Button>(R.id.return_btn).text = list_all_Tasks[7]

    }

    fun buttonOptions() {
        findViewById<Button>(R.id.game_find_dif).setOnClickListener{ b ->
            if(list_tasks.contains(list_all_Tasks[0])){
                b.setBackground(getDrawable(R.drawable.category_custom_bottom))


                list_tasks.remove(list_all_Tasks[0])
            } else {
                b.setBackground(getDrawable(R.drawable.delete_bottom))

                list_tasks.add(list_all_Tasks[0])
            }

           // moveActivity(getString(R.string.face_1), Face_Recognition::class.java)
        }
        findViewById<Button>(R.id.game_find_dif_by_cat).setOnClickListener{
                b ->
            if(list_tasks.contains(list_all_Tasks[1])){
                b.setBackground(getDrawable(R.drawable.category_custom_bottom))


                list_tasks.remove(list_all_Tasks[1])
            } else {
                b.setBackground(getDrawable(R.drawable.delete_bottom))


                list_tasks.add(list_all_Tasks[1])
            }

           // moveActivity(getString(R.string.face_2), Face_Recognition::class.java)
        }
        findViewById<Button>(R.id.game_fix_order).setOnClickListener{
                b ->
            if(list_tasks.contains(list_all_Tasks[2])){
                b.setBackground(getDrawable(R.drawable.category_custom_bottom))


                list_tasks.remove(list_all_Tasks[2])
            } else {
                b.setBackground(getDrawable(R.drawable.delete_bottom))


                list_tasks.add(list_all_Tasks[2])
            }
            //moveActivity(getString(R.string.face_3), Face_Recognition::class.java)
        }
        findViewById<Button>(R.id.game_letter_choose).setOnClickListener{
                b ->
            if(list_tasks.contains(list_all_Tasks[3])){
                b.setBackground(getDrawable(R.drawable.category_custom_bottom))


                list_tasks.remove(list_all_Tasks[3])
            } else {
                b.setBackground(getDrawable(R.drawable.delete_bottom))


                list_tasks.add(list_all_Tasks[3])
            }
        }
        findViewById<Button>(R.id.game_similar_category).setOnClickListener{
                b ->
            if(list_tasks.contains(list_all_Tasks[4])){
                b.setBackground(getDrawable(R.drawable.category_custom_bottom))


                list_tasks.remove(list_all_Tasks[4])
            } else {
                b.setBackground(getDrawable(R.drawable.delete_bottom))


                list_tasks.add(list_all_Tasks[4])
            }
        }
        findViewById<Button>(R.id.game_voice).setOnClickListener{
                b ->
            if(list_tasks.contains(list_all_Tasks[5])){
                b.setBackground(getDrawable(R.drawable.category_custom_bottom))


                list_tasks.remove(list_all_Tasks[5])
            } else {
                b.setBackground(getDrawable(R.drawable.delete_bottom))


                list_tasks.add(list_all_Tasks[5])
            }
        }
        findViewById<Button>(R.id.game_what_in_pic).setOnClickListener{
                b ->
            if(list_tasks.contains(list_all_Tasks[6])){
                b.setBackground(getDrawable(R.drawable.category_custom_bottom))


                list_tasks.remove(list_all_Tasks[6])
            } else {
                b.setBackground(getDrawable(R.drawable.delete_bottom))


                list_tasks.add(list_all_Tasks[6])
            }
        }
        findViewById<Button>(R.id.return_btn).setOnClickListener{
                b ->
            if(list_tasks.contains(list_all_Tasks[7])){
                b.setBackground(getDrawable(R.drawable.category_custom_bottom))



                list_tasks.remove(list_all_Tasks[7])
            } else {
                b.setBackground(getDrawable(R.drawable.delete_bottom))


                list_tasks.add(list_all_Tasks[7])
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