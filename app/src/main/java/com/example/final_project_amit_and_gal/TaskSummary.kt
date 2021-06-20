package com.example.final_project_amit_and_gal

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_task_summary.*

class TaskSummary : AppCompatActivity() {
    @SuppressLint("ResourceType")
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
        findViewById<Button>(R.id.save_button).setOnClickListener{

          // it.setBackgroundResource(Color.GREEN)
        }

        findViewById<Button>(R.id.return_button).setOnClickListener{
            var intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
        }
    }
    fun nameToNumber(name :String): String {
        when(name) {
            "תרגול רץ ארוך" -> return "60"
            "תרגול רץ בינוני" -> return "40"
            "תרגול רץ קצר" -> return "20"
            else -> return "10"
        }

    }
}