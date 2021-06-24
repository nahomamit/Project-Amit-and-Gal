package com.example.final_project_amit_and_gal

import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.Display
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.final_project_amit_and_gal.cards_games.*
import java.util.*
import kotlin.concurrent.schedule

open class SharedFunctions: AppCompatActivity() {
  lateinit var tabsDao: TabDatabaseDao
    lateinit var db: TabDataBase
    override fun onBackPressed(){
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
    fun nextExcercize(questions: Int): Class<out AppCompatActivity> {
        val type:String = intent.getStringExtra("type").toString()
        if(questions == 1){
            return TaskSummary::class.java
        }
        if(type == "0") {
            var chosen = this.javaClass;
            return chosen
        }

        if((questions-1)%5 != 0){
            var chosen = this.javaClass;
            return chosen
        } else {
            val exc_arr = mutableListOf(
                find_the_diffrent::class.java,
                letters_choose::class.java,
                find_the_different_category::class.java,
                fix_letter_order::class.java,
                similar_category::class.java,
                whats_in_the_picture::class.java,
                VoiceReco::class.java
            )
            exc_arr.remove(this.javaClass)
            var chosen = exc_arr.random()

            return chosen
        }


    }
    fun nextActivity(num :Int,questions:Int, score:Int){
        val next_exc = nextExcercize(questions)
        val intent = Intent(this, next_exc)
        Log.i("type", getIntent().getStringExtra("type"))
        if(num == 0) {
            findViewById<ImageView>(R.id.incorrect_img).visibility = View.VISIBLE
        } else {
            findViewById<ImageView>(R.id.correct_img).visibility = View.VISIBLE
        }
        intent.putExtra("time",(questions-1).toString())
        intent.putExtra("score",(score+num).toString())
        intent.putExtra("type",getIntent().getStringExtra("type").toString())
        intent.putExtra("name", getIntent().getStringExtra("name"))
        Log.i("NAME",  getIntent().getStringExtra("name"))
        Timer("SettingUp", false).schedule(500) {
            startActivity(intent)
        }
    }

     fun initDB() {
        db = Room.databaseBuilder(
            applicationContext,
            TabDataBase::class.java,
            "tabs_database"
        ).allowMainThreadQueries().build()
        tabsDao = db.tabDao
    }

    fun getMargin(): List<Int> {
        val display: Display = windowManager.defaultDisplay
        var w: Int = display.getWidth()
        var h: Int = display.getHeight()
        return listOf<Int>(w, h)
    }


}