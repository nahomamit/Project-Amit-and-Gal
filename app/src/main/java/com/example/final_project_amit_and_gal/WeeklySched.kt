package com.example.final_project_amit_and_gal

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.final_project_amit_and_gal.cards_games.*
import java.sql.Types.NULL
import java.util.*


class WeeklySched : SharedFunctions() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weekly_sched)
        var calen = findViewById<CalendarView>(R.id.calendarView)
        calen.minDate = calen.getDate()
        var day1 = 0
        var month1  = 0
        var year1  = 0

        calen.setOnDateChangeListener(CalendarView.OnDateChangeListener { _,year,month,day ->
            Log.i("month", month.toString())
            Log.i("day", day.toString())
            Log.i("year",year.toString())

            year1 = year
            day1 = day
            month1 = month
            resetTasks()



                todayTasks(year,month,day)


        })
        var edit_btn = findViewById<Button>(R.id.add_tasks)
        edit_btn.setOnClickListener{
            if(day1 == 0) {
                Toast.makeText(applicationContext,
                    getString(R.string.select_day), Toast.LENGTH_SHORT).show()
            } else {
            editEvent(year1,month1,day1)
            }
        }
        var delete_btn = findViewById<Button>(R.id.delete_tasks)
        delete_btn.setOnClickListener{
            if(day1 == 0) {
                Toast.makeText(applicationContext,
                    getString(R.string.select_day), Toast.LENGTH_SHORT).show()
            } else {
                deleteEvent(year1,month1,day1)
            }
        }
        //todayTasks(calen, day, year)
    }

    private fun deleteEvent(year: Int, month: Int, day: Int) {
        var pref = getSharedPreferences("Weekly", Context.MODE_PRIVATE)
        var edit = pref.edit()
        edit.remove(day.toString() +month.toString() +year.toString())
        edit.commit()
        var btn1 = findViewById<Button>(R.id.task1)
        var btn2 = findViewById<Button>(R.id.task2)
        var btn3 = findViewById<Button>(R.id.task3)
        var btn4 = findViewById<Button>(R.id.task4)
        btn1.text = ""
        btn2.text = ""
        btn3.text = ""
        btn4.text = ""
        resetTasks()

    }

    private fun todayTasks(year: Int, month: Int, day: Int) {
        var pref = getSharedPreferences("Weekly", Context.MODE_PRIVATE)
        var tasks = pref.getStringSet(day.toString() +month.toString() +year.toString(),null)

        var btn1 = findViewById<Button>(R.id.task1)
        var btn2 = findViewById<Button>(R.id.task2)
        var btn3 = findViewById<Button>(R.id.task3)
        var btn4 = findViewById<Button>(R.id.task4)

        if (tasks == null) {
            btn1.text = ""
            btn2.text = ""
            btn3.text = ""
            btn4.text = ""
            return
        } else {
            for(i in 0..tasks.size) {
                when(i) {
                   0-> btn1.text = tasks.elementAt(0).toString()
                   1-> btn2.text = tasks.elementAt (1).toString()
                   2-> btn3.text = tasks.elementAt (2).toString()
                   3-> btn4.text = tasks.elementAt (3).toString()
                }
            }
            btn1.setOnClickListener{
               // if(today == 1) {
                    stringToActivity(btn1.text.toString())
                //} else {
                  //  onlyFromToday()
                //}
            }
            btn2.setOnClickListener{
                //if (today ==1){
                stringToActivity(btn2.text.toString())
               // } else {
                 //   onlyFromToday()
               // }
            }
            btn3.setOnClickListener{
            //    if (today ==1){
                stringToActivity(btn3.text.toString())
           // } else {
            //    onlyFromToday()
            //}
            }
            btn4.setOnClickListener{
             //   if (today ==1){
                stringToActivity(btn4.text.toString())
            //} else {
          //  onlyFromToday()
       // }
            }


        }

    }
    private fun resetTasks() {
        var confirm_btn = findViewById<Button>(R.id.confirm)
       var spinner1 = findViewById<Spinner>(R.id.task1s)
      var spinner2 = findViewById<Spinner>(R.id.task2s)
        var spinner3 = findViewById<Spinner>(R.id.task3s)
       var spinner4 =  findViewById<Spinner>(R.id.task4s)
        spinner1.setSelection(0)
        spinner2.setSelection(0)
        spinner3.setSelection(0)
        spinner4.setSelection(0)
        spinner1.visibility = View.INVISIBLE
        spinner2.visibility = View.INVISIBLE
        spinner3.visibility = View.INVISIBLE
        spinner4.visibility = View.INVISIBLE
        confirm_btn.visibility = View.INVISIBLE
    }
    private fun editEvent(year: Int, month: Int, day: Int) {
        var pref = getSharedPreferences("Weekly", Context.MODE_PRIVATE)
        var edit = pref.edit()

        var day_string = day.toString() +month.toString() +year.toString()
        var spinner1 = findViewById<Spinner>(R.id.task1s)
        var spinner2 = findViewById<Spinner>(R.id.task2s)
        var spinner3 = findViewById<Spinner>(R.id.task3s)
        var spinner4 = findViewById<Spinner>(R.id.task4s)
        spinner1.visibility = View.VISIBLE
        spinner2.visibility = View.VISIBLE
        spinner3.visibility = View.VISIBLE
        spinner4.visibility = View.VISIBLE
        var confirm_btn = findViewById<Button>(R.id.confirm)
        confirm_btn.visibility = View.VISIBLE

        confirm_btn.setOnClickListener{

            var counter = 4
            var task1 = spinner1.selectedItem.toString()
            var task2 = spinner2.selectedItem.toString()
            var task3 = spinner3.selectedItem.toString()
            var task4 = spinner4.selectedItem.toString()
            var default_choose = getString(R.string.choose_exc)
            if(task1 == default_choose){
                task1 = getString(R.string.not_init1)
                counter--
            }
            if(task2 == default_choose){
                task2 = getString(R.string.not_init2)
                counter--
            }
            if(task3 == default_choose){
                task3 = getString(R.string.not_init3)
                counter--
            }
            if(task4 == default_choose){
                task4 = getString(R.string.not_init4)
                counter--
            }
            var full_tasks = mutableSetOf<String>(task1, task2, task3,task4)
            Log.i("tasks size", full_tasks.toString())
            if (counter == 0){
                Toast.makeText(applicationContext,
                    getString(R.string.must_choose), Toast.LENGTH_SHORT).show()
            } else {
                if (full_tasks.size < 4) {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.selecting_activity_err), Toast.LENGTH_SHORT
                    ).show()
                } else {
                    deleteCorrent(day_string)
                    edit.putStringSet(day_string, full_tasks)
                    edit.commit()
                    resetTasks()
                }
            }
        }
    }

    private fun deleteCorrent(dayString: String) {
        var btn1 = findViewById<Button>(R.id.task1)
        var btn2 = findViewById<Button>(R.id.task2)
        var btn3 = findViewById<Button>(R.id.task3)
        var btn4 = findViewById<Button>(R.id.task4)
        btn1.text = ""
        btn2.text = ""
        btn3.text = ""
        btn4.text = ""
        var pref = getSharedPreferences("Weekly", Context.MODE_PRIVATE)
        var edit = pref.edit()
        edit.remove(dayString)
        edit.commit()

    }
    private fun onlyFromToday() {
        Toast.makeText(applicationContext,
            getString(R.string.only_today), Toast.LENGTH_SHORT).show()
    }
    private fun stringToActivity(name:String)  {
        Log.i("SWITCH", name)
        when (name) {
            getString(R.string.game_short) -> {
                moveActivity("20",randomActivity(),"1", name)

            }
            getString(R.string.game_med) -> {
                moveActivity("40",randomActivity(),"1",name)
            }
            getString(R.string.game_long) -> {
                moveActivity("60",randomActivity(),"1", name)
            }
            getString(R.string.game_whats_in_the_pic) -> {
                moveActivity("10",whats_in_the_picture::class.java,"0", name)
            }
            getString(R.string.game_find_dif) -> {
                moveActivity("10",find_the_diffrent::class.java,"0",name)            }
            getString(R.string.game_find_dif_by_cat) -> {
                moveActivity("10",find_the_different_category::class.java,"0", name)
            }
            getString(R.string.game_fix_letter_order)->{
                 moveActivity("10",fix_letter_order::class.java,"0", name)
            }
            getString(R.string.game_similar_category) -> {
                moveActivity("10",similar_category::class.java,"0", name)
            }
            getString(R.string.game_letter_choose) -> {
               moveActivity("10",letters_choose::class.java,"0", name)
            }

            else -> { // Note the block
                Toast.makeText(applicationContext,
                    getString(R.string.choose_activity_err), Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun moveActivity(time: String, task: Class<out AppCompatActivity>,type :String, name: String ) {
        val intent = Intent(this,task)
        intent.putExtra("time", time)
        intent.putExtra("score", "0")
        intent.putExtra("type", type)
        intent.putExtra("name", name)


        startActivity(intent)
    }

}