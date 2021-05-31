package com.example.final_project_amit_and_gal.cards_games

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.final_project_amit_and_gal.MainActivity
import com.example.final_project_amit_and_gal.R

class find_the_diffrent : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_the_diffrent)
        val time:String = intent.getStringExtra("time").toString()
        val questions:Int = time.toInt()
        val score:Int = intent.getStringExtra("score").toInt()
        val score_text = findViewById<TextView>(R.id.score)
        score_text.text = score.toString()
        val time_left = findViewById<TextView>(R.id.remaining_questions)
        time_left.text = "שאלות שנותרו:" + time
        val text = findViewById<TextView>(R.id.task_for_costumer)
        text.text = "מי יוצא מן הכלל?"
        val back = findViewById<ImageView>(R.id.return_btn)
        back.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("האם אתה מעוניין לסיים את התרגול ולחזור לתפריט הראשי?")
                .setCancelable(false)
                .setPositiveButton("כן") { dialogInterface: DialogInterface, i: Int ->
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                .setNegativeButton("לא") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }

        val correct_ans = findViewById<Button>(R.id.ans_1)
        correct_ans.setOnClickListener {
            correct_ans.setBackgroundResource(R.color.Green)
            nextActivity(1,questions, score)
        }
        val wrong_ans = findViewById<Button>(R.id.ans_2)
        wrong_ans.setOnClickListener{
            wrong_ans.setBackgroundResource(R.color.colorAccent)
            correct_ans.setBackgroundResource(R.color.Green)
            nextActivity(0,questions, score)
        }
        val wrong_ans2 = findViewById<Button>(R.id.ans_3)
        wrong_ans2.setOnClickListener{
            wrong_ans2.setBackgroundResource(R.color.colorAccent)
            correct_ans.setBackgroundResource(R.color.Green)
            nextActivity(0,questions, score)
        }
        val wrong_ans3 = findViewById<Button>(R.id.ans_4)
        wrong_ans3.setOnClickListener{
            wrong_ans3.setBackgroundResource(R.color.colorAccent)
            correct_ans.setBackgroundResource(R.color.Green)
            nextActivity(0,questions, score)
        }
    }
    fun nextExcercize(questions:Int): Class<out AppCompatActivity> {
        val exc_arr = listOf(whats_in_the_picture::class.java)
        val chosen = exc_arr.random()
        if(questions == 1){
            return MainActivity::class.java
        }
        return chosen
    }
    fun nextActivity(num :Int,questions:Int, score:Int){
        val next_exc = nextExcercize(questions)
        val intent = Intent(this, next_exc)
        intent.putExtra("time",(questions-1).toString())
        intent.putExtra("score",(score+num).toString())
        startActivity(intent)
    }
}