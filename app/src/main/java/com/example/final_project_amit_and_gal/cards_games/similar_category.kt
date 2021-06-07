package com.example.final_project_amit_and_gal.cards_games

import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import com.example.final_project_amit_and_gal.*
import java.io.InputStream
import java.lang.Exception
import java.util.*

class similar_category : AppCompatActivity() {
    private lateinit var tabsDao: TabDatabaseDao
    private lateinit var db: TabDataBase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_similar_category)
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
        text.text = "מה מהבאים מאותו " +
                "קטגוריה של התמונה"

        val back = findViewById<ImageView>(R.id.return_btn)
        back.setOnClickListener {
            back_btn()
        }
        val arr_ans = correctAns()
        setAnswers(questions, score)
    }

    fun nextExcercize(questions: Int): Class<out AppCompatActivity> {
        val exc_arr = listOf(find_the_diffrent::class.java
            ,whats_in_the_picture::class.java,
            letters_choose::class.java,
            find_the_different_category::class.java,
            fix_letter_order::class.java,
            similar_category::class.java)
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
        Collections.shuffle(answers_arr)
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

    private fun getAnsBtnList(): Array<Button> {
        val ans1 = findViewById<Button>(R.id.ans_1)
        val ans2 = findViewById<Button>(R.id.ans_2)
        val ans3 = findViewById<Button>(R.id.ans_3)
        val ans4 = findViewById<Button>(R.id.ans_4)
        return (arrayOf<Button>(ans1, ans2, ans3, ans4))
    }

    private fun setAnswers(questions: Int, score: Int) {
        val answerBtnArr = getAnsBtnList()
        var temp: Pair<MutableList<Tab>, Int>?

        do {
            temp = getTabs()
        } while(temp == null)

        val tabs = temp.first
        val wrong = temp.second

        tabs.forEachIndexed{ind, t ->
            try {
                Log.i("Tab setAnswer "+ ind, tabs[ind].toString())
            } catch (e: Exception) {
                Log.e("Error", "error in tab number " + ind)
            }
        }

        setButtonText(answerBtnArr, tabs, wrong, questions, score)

    }

    private fun setButtonText(answerBtnArr: Array<Button>, tabs: MutableList<Tab>,
                              correct: Int, questions: Int, score: Int) {
        val correct_ans = answerBtnArr[correct]
        answerBtnArr.forEachIndexed { ind, btn ->
            try {
                //  val ims: InputStream = assets.open("images/" + tabs.get(ind).url)
                btn.text = tabs.get(ind).name
                ////val d = Drawable.createFromStream(ims, null)
                //  if (d != null) {
                //    btn.setImageDrawable(d)
                if (ind != correct) {
                    btn.setOnClickListener{wrongAnsOnClick(btn, correct_ans, questions, score)}
                } else {
                    btn.setOnClickListener {currentAnsOnClick(btn, questions, score)}
                }
                //}
            } catch (e: Exception) {
                Log.e("Error setButtonText", "Tab number: " + ind)
            }

        }
    }

    private fun getTabs(): Pair<MutableList<Tab>, Int>? {


        val tabs: MutableList<Tab>
        var correct = 0
        var in_picture = tabsDao.getOneTab()
        var correct_ans = tabsDao.getTabByCategory(in_picture.category)
        tabs = tabsDao.get3TabsByNotCategory(correct_ans.category)
        tabs.add(correct_ans)
        tabs.shuffle()
        val ims: InputStream = assets.open("images/" + in_picture.url)
        val d = Drawable.createFromStream(ims, null)
        findViewById<ImageView>(R.id.picture).setImageDrawable(d)
        correct = tabs.indexOf(correct_ans)
        Log.i("Shuffle", "correct is: " +correct)


        tabs.forEachIndexed{ind, t ->
            try {
                Log.i("Tab getTabs " +ind , t.toString())
            } catch (e: Exception) {
                if (t == null) {
                    Log.e("ERROR getTabs", "Error in tab number " + ind + "is null !")
                } else {
                    Log.e("ERROR getTabs", "Error in tab number " + ind + "is NOT null !")
                }
            }
        }
        return Pair(tabs, correct)
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
}