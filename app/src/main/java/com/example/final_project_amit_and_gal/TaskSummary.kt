package com.example.final_project_amit_and_gal

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_task_summary.*
import java.io.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import java.util.jar.Manifest

class TaskSummary : SharedFunctions() {
    @SuppressLint("ResourceType")
    private val REQUEST_CODE = 101
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_summary)
       var progressBar = findViewById<ProgressBar>(R.id.progress1) as ProgressBar
      // Log.i("progress", progressBar.toString())
        val name:String = intent.getStringExtra("name")
        val score:String = intent.getStringExtra("score").toString()
        var maxScore = nameToNumber(name)
        progressBar.progress = score.toInt()
        progressBar.max = maxScore.toInt()
        var total_grade = progressBar.progress/progressBar.max*100
        when(total_grade) {
            in 80..100 -> progressBar.setBackgroundColor(Color.GREEN)
            in 50..79 -> progressBar.setBackgroundColor(Color.YELLOW)
        }
        var progress = score + "/" + maxScore
        findViewById<TextView>(R.id.score).text = progress
        findViewById<TextView>(R.id.task_name).text = name
        var saveBtn = findViewById<Button>(R.id.save_button)
        var backBtn = findViewById<Button>(R.id.return_button)
        saveBtn.setOnClickListener{
            saveBtn.visibility = View.INVISIBLE
            backBtn.visibility = View.INVISIBLE
            val relativeLayout: RelativeLayout= findViewById(R.id.layout_screen)
            //setupPermissions()
            //ActivityCompat.requestPermissions(this,
              //  arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE);
            storeImage(getScreenShot(relativeLayout))
            saveBtn.visibility = View.VISIBLE
            backBtn.visibility = View.VISIBLE
          // it.setBackgroundResource(Color.GREEN)
        }

        backBtn.setOnClickListener{
            var intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
        }
    }
    fun nameToNumber(name: String): String {
        when(name) {
            "תרגול רץ ארוך" -> return "60"
            "תרגול רץ בינוני" -> return "40"
            "תרגול רץ קצר" -> return "20"
            else -> return "10"
        }

    }
    private fun getScreenShot(view: View): Bitmap {
        val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) bgDrawable.draw(canvas)
        else canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        return returnedBitmap
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun storeImage(bitmap: Bitmap):Uri{
        var title = LocalDateTime.now().toString()

        // Save image to gallery
        val savedImageURL = MediaStore.Images.Media.insertImage(
            contentResolver,
            bitmap,
            title,
            "Image of $title"
        )

        // Parse the gallery image url to uri
        return Uri.parse(savedImageURL)
    }
    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i("TAG", "Permission to record denied")
            //makeRequest()
        }
    }

   // Extension function to show toast message
    fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
}