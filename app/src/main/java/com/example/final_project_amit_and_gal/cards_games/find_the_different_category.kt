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
import kotlin.random.Random

private lateinit var tabsDao: TabDatabaseDao
private lateinit var db: TabDataBase

class find_the_different_category : AppCompatActivity() {
    private var hint_count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_the_different_category)
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
        task.text = "מה יוצא מן הכלל?"

        val back = findViewById<ImageView>(R.id.return_btn)
        back.setOnClickListener { backBtnOnClick() }

        setAnswers(questions, score)

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

        setButtonImages(answerBtnArr, tabs, wrong, questions, score)

    }

    private fun setButtonImages(answerBtnArr: Array<Button>, tabs: MutableList<Tab>,
                                correct: Int, questions: Int, score: Int) {
        val correct_ans = answerBtnArr[correct]
        hint(correct)
        answerBtnArr.forEachIndexed { ind, btn ->
            try {
                btn.text = tabs.get(ind).name
                if (ind != correct) {
                    btn.setOnClickListener{wrongAnsOnClick(btn, correct_ans, questions, score)}
                } else {
                    btn.setOnClickListener {currentAnsOnClick(btn, questions, score)}
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

    private fun currentAnsOnClick(correct_ans: Button, questions: Int, score: Int ) {
        correct_ans.setBackgroundResource(R.color.Green)
        nextActivity(1,questions, score)
    }

    private fun wrongAnsOnClick(wrong_ans: Button, correct_ans: Button, questions: Int, score: Int) {
        wrong_ans.setBackgroundResource(R.color.colorAccent)
        correct_ans.setBackgroundResource(R.color.Green)
        nextActivity(0,questions, score)
    }

    private fun nextExcercize(questions:Int): Class<out AppCompatActivity> {
        val type:String = intent.getStringExtra("type").toString()
        if(questions == 0){
            return TaskSummary::class.java
        }
        if(type == "0") {
            var chosen = find_the_different_category::class.java;
            return chosen
        }
        Log.i("tag",questions.toString())
        if((questions-1)%5 != 0){
            var chosen = find_the_different_category::class.java;
            return chosen
        } else {
            val exc_arr = listOf(
                find_the_diffrent::class.java, whats_in_the_picture::class.java,
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
        intent.putExtra("time",(questions-1).toString())
        intent.putExtra("score",(score+num).toString())
        intent.putExtra("type",getIntent().getStringExtra("type").toString())
        intent.putExtra("name", getIntent().getStringExtra("name"))
        startActivity(intent)
    }

    private fun backBtnOnClick() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(getString(R.string.sure_end_excercize))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.yes)) { dialogInterface: DialogInterface, i: Int ->
                val intent = Intent(this, MainMenu::class.java)
                startActivity(intent)
            }
            .setNegativeButton(getString(R.string.no)) { dialog, id ->
                // Dismiss the dialog
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
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