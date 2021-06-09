package com.example.final_project_amit_and_gal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader


class MainActivity : AppCompatActivity() {
    private lateinit var tabsDao: TabDatabaseDao
    private lateinit var db: TabDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = Room.databaseBuilder(
            applicationContext,
            TabDataBase::class.java,
            "tabs_database"
        ).allowMainThreadQueries().build()
        tabsDao = db.tabDao

        tabsDao.clear()
        createDB()
        Log.i("Num Of Rows", tabsDao.getNumTabs().toString())
        var name = findViewById<TextView>(R.id.hello_name)
        val sp1 = getSharedPreferences("Login", MODE_PRIVATE)

        val name_text = sp1.getString("Name", null)

        name.text = "שלום " + name_text
        val button_short = findViewById<Button>(R.id.short_time)
        button_short.setOnClickListener {
            moveActivity("20")
            /*val intent = Intent(this, ChooseExc::class.java)
            intent.putExtra("time","20")
            intent.putExtra("score","0")
            startActivity(intent)*/
        }
        val button_med = findViewById<Button>(R.id.mid_time)
        button_med.setOnClickListener {
            moveActivity("40")
            /*val intent = Intent(this, ChooseExc::class.java)
            intent.putExtra("time","40")
            intent.putExtra("score","0")
            startActivity(intent)*/
        }
        val button_long = findViewById<Button>(R.id.long_time)
        button_long.setOnClickListener {
            moveActivity("60")
            /*val intent = Intent(this, ChooseExc::class.java)
            intent.putExtra("time","60")
            intent.putExtra("score","0")
            startActivity(intent)*/
        }
    }

    private fun moveActivity(time: String) {
        val intent = Intent(this, ChooseExc::class.java)
        intent.putExtra("time", time)
        //למה צריך את הסקור באקטיביטי הזה ?
        intent.putExtra("score", "0")
        startActivity(intent)
    }

    private fun createDB() {
        var arr = read_data_json()
        if(arr != null) {
            var it = arr.iterator()
            it.forEach {
                try {
                    tabsDao.insert(it)
                } catch (e: Exception) {
                    Log.e("InsertErr", e.toString())
                }
            }
        }


    }

    private fun read_data_json(): Array<Tab>? {
        val filename = "data.json"
        var json: String? = null
        try{
            val inputStream: InputStream = assets.open(filename)
            val br = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
            json = br.readLine()

            val list = Gson().fromJson(json, Array<Tab>::class.java)
            if (list == null) {
                Log.i("amit", "NULL")

            } else {
                return(list)
            }
        } catch (e: Exception) {
            Log.e("ERROR", e.toString())

        }
        return null
    }
}