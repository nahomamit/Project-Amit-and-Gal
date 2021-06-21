package com.example.final_project_amit_and_gal

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.Spinner
import android.widget.Toast
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
      //  dateView = findViewById(R.id.dateView)
        calen.setOnDateChangeListener(CalendarView.OnDateChangeListener { _,year,month,day ->
            Log.i("month", month.toString())
            Log.i("day", day.toString())
            Log.i("year",year.toString())

            year1 = year
            day1 = day
            month1 = month
            resetTasks()
         //  editEvent(month,day,year)
            todayTasks(year,month,day)
        })
        var edit_btn = findViewById<Button>(R.id.add_tasks)
        edit_btn.setOnClickListener{
            if(day1 == 0) {
                Toast.makeText(applicationContext,
                    "בחר יום", Toast.LENGTH_SHORT).show()
            } else {
            editEvent(year1,month1,day1)
            }
        }
        var delete_btn = findViewById<Button>(R.id.delete_tasks)
        delete_btn.setOnClickListener{
            if(day1 == 0) {
                Toast.makeText(applicationContext,
                    "בחר יום", Toast.LENGTH_SHORT).show()
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
        Log.i("task1" , tasks.toString())
        Log.i("task2" , tasks?.elementAt(1).toString())
        Log.i("task3" , tasks?.elementAt(2).toString())
        Log.i("task4" , tasks?.elementAt(3).toString())
  //      btn2.text = tasks.elementAt(1).toString()
    //    btn3.text = tasks.elementAt(2).toString()
      //  btn4.text = tasks.elementAt(3).toString()
        var btn1 = findViewById<Button>(R.id.task1)
        var btn2 = findViewById<Button>(R.id.task2)
        var btn3 = findViewById<Button>(R.id.task3)
        var btn4 = findViewById<Button>(R.id.task4)
        if (tasks == null) {
            Log.i("task","TASK IS NULL")
            btn1.text = ""
            btn2.text = ""
            btn3.text = ""
            btn4.text = ""
            return
        } else {

            btn1.text = tasks.elementAt(0).toString()
            btn2.text = tasks.elementAt(1).toString()
            btn3.text = tasks.elementAt(2).toString()
            btn4.text = tasks.elementAt(3).toString()
            btn1.setOnClickListener{
                Log.i("btn1", "clicked")
                stringToActivity(btn1.text.toString())
            }
            btn2.setOnClickListener{
                Log.i("btn2", "clicked")
                stringToActivity(btn2.text.toString())
            }
            btn3.setOnClickListener{
                Log.i("btn3", "clicked")
                stringToActivity(btn3.text.toString())
            }
            btn4.setOnClickListener{
                Log.i("btn4", "clicked")
                stringToActivity(btn4.text.toString())
            }


        }
        //Log.i("today", day_string.toString())
      //  if(pref.getString(""))
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

       // Log.i("day", day.toString())
        //Log.i("month", month.toString())
       // Log.i("year", year.toString())
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
            var task1 = spinner1.selectedItem.toString()
            var task2 = spinner2.selectedItem.toString()
            var task3 = spinner3.selectedItem.toString()
            var task4 = spinner4.selectedItem.toString()
            var full_tasks = mutableSetOf<String>(task1, task2, task3,task4)
            Log.i("tasks size", full_tasks.size.toString())
            if(full_tasks.size < 4) {
                Toast.makeText(applicationContext,
                    "לא ניתן לבחור את אותו משחק פעמיים", Toast.LENGTH_SHORT).show()
            } else {
                deleteCorrent(day_string)
                edit.putStringSet(day_string,full_tasks)
                edit.commit()
              resetTasks()
            }
        }
            // Log.i("task full" , full_tasks.toString())

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

    private fun stringToActivity(name:String)  {
        when (name) {
            "תרגול רץ קצר" -> {
                moveActivity("20",ChooseExc::class.java,"1", name)

            }
            "תרגול רץ בינוני" -> {
                moveActivity("40",ChooseExc::class.java,"1",name)
            }
            "תרגול רץ ארוך" -> {
                moveActivity("60",ChooseExc::class.java,"1", name)
            }
            "מה בתמונה?" -> {
                moveActivity("10",whats_in_the_picture::class.java,"0", name)
            }
            "מצא את ההבדלים" -> {
                moveActivity("10",find_the_diffrent::class.java,"0",name)            }
            "מצא את הקטגוריה השונה" -> {
                moveActivity("10",find_the_different_category::class.java,"0", name)
            }
            "מילים מבלוגנות"->{
                 moveActivity("10",fix_letter_order::class.java,"0", name)
            }
            "קטגוריה דומה" -> {
                moveActivity("10",similar_category::class.java,"0", name)
            }
            "מילים מבנק אותיות" -> {
               moveActivity("10",letters_choose::class.java,"0", name)
            }

            else -> { // Note the block
                Toast.makeText(applicationContext,
                    "לא הגדרת משחק", Toast.LENGTH_SHORT).show()
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