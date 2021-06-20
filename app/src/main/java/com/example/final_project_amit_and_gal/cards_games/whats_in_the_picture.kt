package com.example.final_project_amit_and_gal.cards_games

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.LightingColorFilter
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import com.example.final_project_amit_and_gal.*
import com.example.final_project_amit_and_gal.R.color.Green
import com.example.final_project_amit_and_gal.R.color.colorAccent
import java.io.InputStream
import java.lang.Exception
import java.util.Collections.shuffle
import kotlin.random.Random

class whats_in_the_picture : AppCompatActivity() {
    @SuppressLint("ResourceAsColor")
    private lateinit var tabsDao: TabDatabaseDao
    private lateinit var db: TabDataBase
    private var hint_count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_whats_in_the_picture)
        initDB()
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
        //    val arr_ans = correctAns()

        setAnswers(questions, score)
        //text.text = current_exercize
    }
    fun nextExcercize(questions: Int): Class<out AppCompatActivity> {
        val type:String = intent.getStringExtra("type").toString()
        if(questions == 1){
            return TaskSummary::class.java
        }
        if(type == "0") {
            var chosen = whats_in_the_picture::class.java;
            return chosen
        }

        if((questions-1)%5 != 0){
            var chosen = whats_in_the_picture::class.java;
            return chosen
        } else {
            val exc_arr = listOf(
                find_the_diffrent::class.java,
                letters_choose::class.java,
                find_the_different_category::class.java,
                fix_letter_order::class.java,
                similar_category::class.java
            )
            var chosen = exc_arr.random()

            return chosen
        }


    }
    fun nextActivity(num :Int,questions:Int, score:Int){
        val next_exc = nextExcercize(questions)
        val intent = Intent(this, next_exc)
        Log.i("type", getIntent().getStringExtra("type"))
        intent.putExtra("time",(questions-1).toString())
        intent.putExtra("score",(score+num).toString())
        intent.putExtra("type",getIntent().getStringExtra("type").toString())
        intent.putExtra("name", getIntent().getStringExtra("name"))
        Log.i("NAME",  getIntent().getStringExtra("name"))
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
                val intent = Intent(this, MainMenu::class.java)
                startActivity(intent)
            }
            .setNegativeButton("לא") { dialog, id ->
                // Dismiss the dialog
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    private fun getAnsBtnList(): Array<Button> {
        val ans1 = findViewById<Button>(R.id.ans_1)
        val ans2 = findViewById<Button>(R.id.ans_2)
        val ans3 = findViewById<Button>(R.id.ans_3)
        val ans4 = findViewById<Button>(R.id.ans_4)
        return (arrayOf<Button>(ans1, ans2, ans3, ans4))
    }

    private fun setAnswers(questions: Int, score: Int) {
        val answerBtnArr = getAnsBtnList()
        var tabs: MutableList<Tab>?

        do {
            tabs = getTabs()
        } while(tabs == null)




        setButtonText(answerBtnArr, tabs,  questions, score)

    }

    private fun setButtonText(answerBtnArr: Array<Button>, tabs: MutableList<Tab>,
                              questions: Int, score: Int) {
        var correct = Random.nextInt(0, 3)
        hint(correct)
        val correct_ans = answerBtnArr[correct]
        answerBtnArr.forEachIndexed { ind, btn ->
            try {

                btn.text = tabs.get(ind).name

                if (ind != correct) {
                    btn.setOnClickListener{wrongAnsOnClick(btn, correct_ans, questions, score)}
                } else {
                    val ims: InputStream = assets.open("images/" + tabs.get(ind).url)
                    val d = Drawable.createFromStream(ims, null)
                    findViewById<ImageView>(R.id.picture).setImageDrawable(d)
                    btn.setOnClickListener {currentAnsOnClick(btn, questions, score)}
                }
                //}
            } catch (e: Exception) {
                Log.e("Error setButtonText", "Tab number: " + ind)
            }

        }
    }

    private fun getTabs(): MutableList<Tab>? {
        val tabs: MutableList<Tab>
        tabs = tabsDao.getFourTabs()
        tabs.shuffle()
        return tabs
    }

    private fun currentAnsOnClick(correct_ans: Button, questions: Int, score: Int ) {
        correct_ans.setBackgroundResource(R.color.Green)
        nextActivity(1,questions, score)
    }

    private fun wrongAnsOnClick(wrong_ans: Button, correct_ans: Button, questions: Int, score: Int) {
        wrong_ans.setBackgroundResource(R.color.colorAccent)
        correct_ans.setBackgroundResource(R.color.Green)
        nextActivity(0,questions, score)
    }

    private fun initDB() {
        db = Room.databaseBuilder(
            applicationContext,
            TabDataBase::class.java,
            "tabs_database"
        ).allowMainThreadQueries().build()
        tabsDao = db.tabDao
    }

    private fun hint(correct: Int){
        val hint1 =  findViewById<ImageView>(R.id.hint)
        hint1.setOnClickListener{
            if(hint_count == 1) {
                return@setOnClickListener
            }

            hint1.setColorFilter(LightingColorFilter(Color.WHITE, Color.GRAY))
            val ans1 = findViewById<Button>(R.id.ans_1)
            val ans2 = findViewById<Button>(R.id.ans_2)
            val ans3 = findViewById<Button>(R.id.ans_3)
            val ans4 = findViewById<Button>(R.id.ans_4)
            loop@ while(true) {
                var x = Random.nextInt(0,3)

                when (x) {
                    correct -> continue@loop
                    0 -> {
                        ans1.visibility = View.INVISIBLE
                        break@loop
                    }
                    1 -> {
                        ans2.visibility = View.INVISIBLE
                        break@loop
                    }
                    2 -> {ans3.visibility = View.INVISIBLE
                        break@loop
                    }
                    3 -> {
                        ans4.visibility = View.INVISIBLE
                        break@loop
                    }
                }
            }
        }

    }
}