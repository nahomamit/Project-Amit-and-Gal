package com.example.final_project_amit_and_gal.cards_games

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.final_project_amit_and_gal.ChooseExc
import com.example.final_project_amit_and_gal.MainActivity
import com.example.final_project_amit_and_gal.R
import com.example.final_project_amit_and_gal.R.color.Green
import com.example.final_project_amit_and_gal.R.color.colorAccent
import java.util.Collections.shuffle

class whats_in_the_picture : AppCompatActivity() {
    @SuppressLint("ResourceAsColor")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_whats_in_the_picture)
        //questions left
        val time:String = intent.getStringExtra("time").toString()
        val questions:Int = time.toInt()
        val time_left = findViewById<TextView>(R.id.remaining_questions)
        time_left.text = "שאלות שנותרו:" + time
        //correct ans until now
        val score:Int = intent.getStringExtra("score").toInt()
        val score_text = findViewById<TextView>(R.id.score)
        score_text.text = score.toString()
        //question text
        val text = findViewById<TextView>(R.id.task_for_costumer)
        text.text = "מה בתמונה?"

        val back = findViewById<ImageView>(R.id.return_btn)
        back.setOnClickListener {
           back_btn()
        }
        val arr_ans = correctAns()

        val correct_ans = findViewById<Button>(arr_ans[0])
        correct_ans.text="true"
        correct_ans.setOnClickListener {
            correct_ans.setBackgroundResource(Green)
            nextActivity(1,questions,score)
        }
        val wrong_ans = findViewById<Button>(arr_ans[1])
        wrong_ans.text = "false"
        wrong_ans.setOnClickListener{
            wrong_ans.setBackgroundResource(colorAccent)
            correct_ans.setBackgroundResource(Green)
           nextActivity(0,questions,score)
        }
        val wrong_ans2 = findViewById<Button>(arr_ans[2])
        wrong_ans2.text = "false"
        wrong_ans2.setOnClickListener{
            wrong_ans2.setBackgroundResource(colorAccent)
            correct_ans.setBackgroundResource(Green)
            nextActivity(0,questions,score)
        }
        val wrong_ans3 = findViewById<Button>(arr_ans[3])
        wrong_ans3.text = "false"
        wrong_ans3.setOnClickListener{
            wrong_ans3.setBackgroundResource(colorAccent)
            correct_ans.setBackgroundResource(Green)
            nextActivity(0,questions,score)
        }
        //text.text = current_exercize
    }
    fun nextExcercize(questions: Int): Class<out AppCompatActivity> {
        val exc_arr = listOf(find_the_diffrent::class.java)
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
    fun correctAns(): List<Int> {
        val answers_arr = listOf(R.id.ans_4,R.id.ans_3,R.id.ans_2,R.id.ans_1)
        shuffle(answers_arr)
        return answers_arr
    }
    fun back_btn(){

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

}