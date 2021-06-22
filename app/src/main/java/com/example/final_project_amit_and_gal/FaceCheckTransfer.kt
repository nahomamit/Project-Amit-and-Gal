package com.example.final_project_amit_and_gal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class FaceCheckTransfer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var intent1 = Intent(this, Face_Recognition::class.java)
        var stringArr = intent.getStringArrayListExtra("name")

        if(stringArr.size > 1) {
            Log.i("ARR_SIZE", stringArr.size.toString())
            stringArr.removeAt(0)
            intent1.putStringArrayListExtra("name", stringArr)
            startActivity(intent1)
        } else {
            var intent2 = Intent(this, MainMenu::class.java)
            startActivity(intent2)
        }
    }
}