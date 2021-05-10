package com.example.final_project_amit_and_gal.cards_games

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.final_project_amit_and_gal.ChooseExc
import com.example.final_project_amit_and_gal.MainActivity
import com.example.final_project_amit_and_gal.R
import com.example.final_project_amit_and_gal.R.color.Green
import com.example.final_project_amit_and_gal.R.color.colorAccent

class whats_in_the_picture : AppCompatActivity() {
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_whats_in_the_picture)
        val time:String = intent.getStringExtra("time").toString()
        val time_left = findViewById<TextView>(R.id.remaining_questions)
        time_left.text = "שאלות שנותרו:" + time
        val text = findViewById<TextView>(R.id.task_for_costumer)
        text.text = "מה בתמונה?"
        val current_exercize = nextExcercize()
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
            correct_ans.setBackgroundResource(Green)
        }
        val wrong_ans = findViewById<Button>(R.id.ans_2)
        wrong_ans.setOnClickListener{
            wrong_ans.setBackgroundResource(colorAccent)
        }
        val wrong_ans2 = findViewById<Button>(R.id.ans_3)
        wrong_ans2.setOnClickListener{
            wrong_ans2.setBackgroundResource(colorAccent)
        }
        val wrong_ans3 = findViewById<Button>(R.id.ans_4)
        wrong_ans3.setOnClickListener{
            wrong_ans3.setBackgroundResource(colorAccent)
        }
        //text.text = current_exercize
    }
    fun nextExcercize(): String {
        val exc_arr = listOf("מה בתמונה?", "בחר ביוצא הדופן", "תגיד בקול מה בתמונה", "בחר ביוצא הדופן 2")
        val chosen = exc_arr.random()
        return chosen
    }
}