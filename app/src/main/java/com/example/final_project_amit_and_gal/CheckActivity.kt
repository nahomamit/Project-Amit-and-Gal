package com.example.final_project_amit_and_gal

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class CheckActivity : AppCompatActivity() {
    private lateinit var tabsDao: TabDatabaseDao
    private lateinit var db: TabDataBase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check)
        try {
            db = Room.databaseBuilder(
                applicationContext,
                TabDataBase::class.java,
                "tabs_database"
            ).allowMainThreadQueries().build()
            tabsDao = db.tabDao
            if (tabsDao.getAllTabs().size < 250) {
                tabsDao.clear()
                throw Exception("Database empty")
            }
        } catch (e: Exception) {
            Log.i("DATABASE", "database created again !")
            createDB()
        }

        var pref = getSharedPreferences("Login", Context.MODE_PRIVATE);
        var weekly = getSharedPreferences("Weekly", Context.MODE_PRIVATE)
        if (pref.getString("Name",null) == null) {

            val intent = Intent(this, FirstTimeEnter::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
        }
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