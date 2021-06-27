package com.example.final_project_amit_and_gal

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Color.RED
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.final_project_amit_and_gal.cards_games.*
import java.util.*
import kotlin.collections.ArrayList

class FaceRecoMenu : AppCompatActivity() {
    var play_length : Int = 0
    var list_tasks : ArrayList<String> = arrayListOf()
    var list_all_Tasks :ArrayList<String> = arrayListOf()
    var buttons :ArrayList<Button> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_face_reco_menu)
        var type = intent.getStringExtra("type")
        buttonInit()
        buildArr(type)
        buttonText()
        buttonOptions()


    }

    override fun onBackPressed() {
        back_btn()
    }
    fun back_btn(){

        val builder = AlertDialog.Builder(this)
        builder.setMessage("האם אתה מעוניין לסיים את התרגול ולחזור לתפריט הראשי?")
            .setCancelable(false)
            .setPositiveButton("כן") { dialogInterface: DialogInterface, i: Int ->
                val intent = Intent(this, MainMenu::class.java)
                startActivity(intent)
            }
            .setNegativeButton("לא") { dialog, id ->
                // Dismiss the dialog
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    fun  buildArr(type:String){
        when(type){
            getString(R.string.face_jaw) -> list_all_Tasks.addAll(
                Arrays.asList(
                        getString (R.string.jaw_1)

                )
            )
            getString(R.string.face_lips) -> list_all_Tasks.addAll(
                Arrays.asList(
                    getString (R.string.lips_1),
                    getString (R.string.lips_2),
                    getString (R.string.lips_3),
                    getString (R.string.lips_5),
                    getString (R.string.lips_6),
                    getString (R.string.lips_7),
                    getString (R.string.lips_8),
                    getString (R.string.lips_9),
                    getString (R.string.lips_10),
                    getString (R.string.lips_13),
                    getString (R.string.lips_14),
                    getString (R.string.lips_16)
                )
            )
            getString(R.string.face_tongue) -> list_all_Tasks.addAll(
                Arrays.asList(
                    getString (R.string.tongue_1),
                    getString (R.string.tongue_2),
                    getString (R.string.tongue_3),
                    getString (R.string.tongue_4),
                    getString (R.string.tongue_6),
                    getString (R.string.tongue_7),
                    getString (R.string.tongue_8),
                    getString (R.string.tongue_9),
                    getString (R.string.tongue_10),
                    getString (R.string.tongue_11),
                    getString (R.string.tongue_12),
                    getString (R.string.tongue_16),
                    getString (R.string.tongue_24)
                    )
            )
            getString(R.string.face_eyes) -> list_all_Tasks.addAll(
                Arrays.asList(
                    getString (R.string.eyes_1),
                    getString (R.string.eyes_2),
                    getString (R.string.eyes_3)


                )
            )
        }


    }

    fun buttonText() {
        for(i in 0..(list_all_Tasks.size-1)){
            buttons[i].text = list_all_Tasks[i]
            buttons[i].visibility = View.VISIBLE

        }
    }
    fun buttonInit() {
        buttons.addAll(
            Arrays.asList(
            findViewById<Button>(R.id.game_find_dif),
        findViewById<Button>(R.id.game_find_dif_by_cat),
        findViewById<Button>(R.id.game_fix_order),
        findViewById<Button>(R.id.game_letter_choose),
        findViewById<Button>(R.id.game_similar_category),
        findViewById<Button>(R.id.game_voice),
        findViewById<Button>(R.id.game_what_in_pic),
        findViewById<Button>(R.id.return_btn),
            findViewById<Button>(R.id.task_9),
            findViewById<Button>(R.id.task_10),
            findViewById<Button>(R.id.task_11),
            findViewById<Button>(R.id.task_12),
            findViewById<Button>(R.id.task_13),
            findViewById<Button>(R.id.task_14)
        )
        )
    }

    fun buttonOptions() {
        for(i in 0..(list_all_Tasks.size-1)) {
            buttons[i].setOnClickListener { b ->
                if (list_tasks.contains(list_all_Tasks[i])) {
                    b.setBackground(getDrawable(R.drawable.category_custom_bottom))


                    list_tasks.remove(list_all_Tasks[i])
                } else {
                    b.setBackground(getDrawable(R.drawable.delete_bottom))

                    list_tasks.add(list_all_Tasks[i])
                }

                // moveActivity(getString(R.string.face_1), Face_Recognition::class.java)
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