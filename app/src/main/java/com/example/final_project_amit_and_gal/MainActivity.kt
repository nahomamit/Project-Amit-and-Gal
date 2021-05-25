package com.example.final_project_amit_and_gal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
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
        val category = tabsDao.getAllTabs()
        category.forEachIndexed { index, element ->
            Log.i(index.toString(), element.toString())
        }
        Log.i("I", "Done printing tabs")


        val button_short = findViewById<Button>(R.id.short_time)
        button_short.setOnClickListener {
            val intent = Intent(this, ChooseExc::class.java)
            intent.putExtra("time","20")
            intent.putExtra("score","0")
            startActivity(intent)
        }
        val button_med = findViewById<Button>(R.id.mid_time)
        button_med.setOnClickListener {
            val intent = Intent(this, ChooseExc::class.java)
            intent.putExtra("time","40")
            intent.putExtra("score","0")
            startActivity(intent)
        }
        val button_long = findViewById<Button>(R.id.long_time)
        button_long.setOnClickListener {
            val intent = Intent(this, ChooseExc::class.java)
            intent.putExtra("time","60")
            intent.putExtra("score","0")
            startActivity(intent)
        }
    }

    private fun createDB() {
        var arr = read_data_json()
        if(arr != null) {
            var it = arr.iterator()
            it.forEach {
                try {
                    Log.i("CreateDB Iter", it.toString())
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
                //var it = list.iterator()
                //it.forEach {Log.i("amit", it.name)}
                return(list)
            }
        } catch(e:Exception) {
            Log.e("ERROR", e.toString())

        }
        return null
    }
}