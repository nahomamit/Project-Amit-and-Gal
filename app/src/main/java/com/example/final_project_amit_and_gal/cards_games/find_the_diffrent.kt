package com.example.final_project_amit_and_gal.cards_games

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.LightingColorFilter
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.final_project_amit_and_gal.*
import java.io.InputStream
import java.lang.Exception
import kotlin.random.Random


class find_the_diffrent : SharedFunctions() {
    private var hint_count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_the_diffrent)

        initDB()

        val t = intent.getStringExtra("time")
        val time:String
        val score:Int
        if (t == null) {
            throw Exception("extra t is null !")
        }
        time = t.toString()
        val questions:Int = time.toInt()
        if (intent.getStringExtra("score") != null){
            score = intent.getStringExtra("score").toInt()
        } else {
            throw Exception("extra score is null !")
        }
        val score_text = findViewById<TextView>(R.id.score)
        score_text.text = score.toString()

        val time_left = findViewById<TextView>(R.id.remaining_questions)
        time_left.text = getString(R.string.remain_questions) + time

        val task = findViewById<TextView>(R.id.task_for_costumer)
        task.text = getString(R.string.who_is_diff)

        val back = findViewById<ImageView>(R.id.return_btn)
        back.setOnClickListener { back_btn() }

        setAnswers(questions, score)
        /*
        val correct_ans = findViewById<Button>(R.id.ans_1)
        correct_ans.setOnClickListener {currentAnsOnClick(correct_ans, questions, score)}

        val wrong_ans = findViewById<Button>(R.id.ans_2)
        wrong_ans.setOnClickListener{wrongAnsOnClick(wrong_ans, correct_ans, questions, score)}

        val wrong_ans2 = findViewById<Button>(R.id.ans_3)
        wrong_ans2.setOnClickListener{wrongAnsOnClick(wrong_ans2, correct_ans, questions, score)}

        val wrong_ans3 = findViewById<Button>(R.id.ans_4)
        wrong_ans3.setOnClickListener{wrongAnsOnClick(wrong_ans3, correct_ans, questions, score)}

         */
    }

    private fun getAnsBtnList(): Array<ImageView> {
        val ans1 = findViewById<ImageView>(R.id.picture1)
        val ans2 = findViewById<ImageView>(R.id.picture2)
        val ans3 = findViewById<ImageView>(R.id.picture3)
        val ans4 = findViewById<ImageView>(R.id.picture4)
        return (arrayOf<ImageView>(ans1, ans2, ans3, ans4))
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

        setButtonImages(answerBtnArr, tabs, wrong, questions, score)

    }

    private fun setButtonImages(answerBtnArr: Array<ImageView>, tabs: MutableList<Tab>,
                                correct: Int, questions: Int, score: Int) {
        val correct_ans = answerBtnArr[correct]
        hint(correct)
        answerBtnArr.forEachIndexed { ind, btn ->
            try {
                val ims: InputStream = assets.open("images/" + tabs.get(ind).url)
                val d = Drawable.createFromStream(ims, null)
                if (d != null) {
                    btn.setImageDrawable(d)
                    if (ind != correct) {
                        btn.setOnClickListener{wrongAnsOnClick(btn, correct_ans, questions, score)}
                    } else {
                        btn.setOnClickListener {currentAnsOnClick(btn, questions, score)}
                    }
                }
            } catch (e: Exception) {
                Log.e("Error setButtonImages", "Tab number: " + ind)
            }

        }
    }

    private fun getTabs(): Pair<MutableList<Tab>, Int>? {
        val categories = tabsDao.getCategories()
        var mainCategory: String
        var diffCategory: String
        val tabs: MutableList<Tab>
        val diffTab: Tab
        var correct = 0

        do {
            mainCategory = categories[(0..categories.size-1).random()]
        } while (mainCategory == "כללי")

        do {
            diffCategory = categories[(0..categories.size-1).random()]
        } while (mainCategory == diffCategory)

        tabs = tabsDao.get3TabsByCategory(mainCategory)
        if (tabs.size < 3) {
            Log.i("Size Err ", tabs.size.toString())
            return null
        }

        diffTab = tabsDao.getTabByCategory(diffCategory)
        if (diffTab == null) {
            Log.e("Error ", "diff tab is null try again")
            return null
        }

        tabs.add(diffTab)
        tabs.shuffle()

        correct = tabs.indexOf(diffTab)
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

    private fun currentAnsOnClick(correct_ans: ImageView, questions: Int, score: Int ) {
        correct_ans.setBackgroundResource(R.color.Green)
        nextActivity(1,questions, score)
    }

    private fun wrongAnsOnClick(wrong_ans: ImageView, correct_ans: ImageView, questions: Int, score: Int) {
        wrong_ans.setBackgroundResource(R.color.colorAccent)
        correct_ans.setBackgroundResource(R.color.Green)
        nextActivity(0,questions, score)
    }


    private fun hint(correct: Int){
        val hint1 =  findViewById<ImageView>(R.id.hint)
        hint1.setOnClickListener{
            if(hint_count == 1) {
                return@setOnClickListener
            }
            hint1.setColorFilter(LightingColorFilter(Color.WHITE, Color.GRAY))
            val ans1 = findViewById<ImageView>(R.id.picture1)
            val ans2 = findViewById<ImageView>(R.id.picture2)
            val ans3 = findViewById<ImageView>(R.id.picture3)
            val ans4 = findViewById<ImageView>(R.id.picture4)
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