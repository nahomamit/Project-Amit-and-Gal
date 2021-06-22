package com.example.final_project_amit_and_gal.cards_games

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
import java.io.InputStream
import java.lang.Exception
import java.util.*
import kotlin.random.Random

class similar_category : SharedFunctions() {
    private var hint_count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_similar_category)
        initDB()
        //questions left
        val time:String = intent.getStringExtra("time").toString()
        val questions:Int = time.toInt()
        val time_left = findViewById<TextView>(R.id.remaining_questions)
        time_left.text = " שאלות שנותרו:" + time
        //correct ans until now
        val score:Int = intent.getStringExtra("score").toInt()
        val score_text = findViewById<TextView>(R.id.score)
        score_text.text = score.toString()
        score_text.visibility = View.INVISIBLE
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

    fun correctAns(): List<Int> {
        val answers_arr = listOf(R.id.ans_4,R.id.ans_3,R.id.ans_2,R.id.ans_1)
        Collections.shuffle(answers_arr)
        return answers_arr
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
        hint(correct)
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
        var correct_ans = tabsDao.getTabByCategory(in_picture.subcategory)
        tabs = tabsDao.get3TabsByNotCategory(correct_ans.subcategory)
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

    private fun hint(correct: Int){
        val hint1 =  findViewById<ImageView>(R.id.hint)
        hint1.setOnClickListener{
            if(hint_count == 1 ) {
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