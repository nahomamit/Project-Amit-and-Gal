package com.example.final_project_amit_and_gal.cards_games

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.room.Room
import com.example.final_project_amit_and_gal.*
import java.io.InputStream
import java.lang.Exception
import java.util.*

class letters_choose : AppCompatActivity() {
    private var mistakes = 0
    protected var correct: Int = 0

    private lateinit var tabsDao: TabDatabaseDao
    private lateinit var db: TabDataBase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_letters_choose)
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
        text.text = "מה בתמונה?" +"\n"+
                " בחר אותיות מהבנק"

        val back = findViewById<ImageView>(R.id.return_btn)
        back.setOnClickListener {
            back_btn()
        }
        setAnswers(questions, score)
    }
    fun nextExcercize(questions: Int): Class<out AppCompatActivity> {
        val exc_arr = listOf(find_the_diffrent::class.java
            ,whats_in_the_picture::class.java,
            letters_choose::class.java,
            find_the_different_category::class.java,
            fix_letter_order::class.java)
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
        val ans1 = findViewById<Button>(R.id.letter1)
        val ans2 = findViewById<Button>(R.id.letter2)
        val ans3 = findViewById<Button>(R.id.letter3)
        val ans4 = findViewById<Button>(R.id.letter4)
        val ans5 = findViewById<Button>(R.id.letter5)
        val ans6 = findViewById<Button>(R.id.letter6)
        val ans7 = findViewById<Button>(R.id.letter7)
        val ans8 = findViewById<Button>(R.id.letter8)
        val ans9 = findViewById<Button>(R.id.letter9)
        val ans10 = findViewById<Button>(R.id.letter10)
        return (arrayOf<Button>(ans1, ans2, ans3, ans4,ans5,ans6,ans7,ans8,ans9,ans10))
    }

    @SuppressLint("ResourceType")
    private fun setAnswers(questions: Int, score: Int) {
        val answerBtnArr = getAnsBtnList()
        var temp: Tab = getTabs()
        val ims: InputStream = assets.open("images/" + temp.url)
        val d = Drawable.createFromStream(ims, null)
        findViewById<ImageView>(R.id.picture).setImageDrawable(d)
        var letters :List<Char> = temp.name.toList()
        val linear_layout = findViewById(R.id.full_word) as LinearLayout
        Log.i("array",letters.toString())

        val id_list: MutableList<Int> = mutableListOf()
        for (letter in letters) {
            val button = Button(this)
            button.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,
                1F
            )
            button.text = letter.toString()
            button.setBackgroundColor(Color.WHITE)
            button.setTextColor(Color.RED)
            button.visibility = View.INVISIBLE

            button.id = View.generateViewId()
            linear_layout.addView(button)
            id_list.add(button.id)

        }
        Log.i("array size",letters.size.toString())
        var letter_bank =  merge(letters,letterABC(10-letters.size))
        Log.i("array size",letters.size.toString())

        letter_bank.shuffled()

        setButtonText(answerBtnArr, letters,letter_bank, questions, score, id_list)

    }

    private fun setButtonText(answerBtnArr: Array<Button>,  tabs :List<Char> ,bank :List<Char>,
                              questions: Int, score: Int, id_list: MutableList<Int>) {

        answerBtnArr.forEachIndexed { ind, btn ->
            try {
                //  val ims: InputStream = assets.open("images/" + tabs.get(ind).url)
                btn.text = bank[ind].toString()
                ////val d = Drawable.createFromStream(ims, null)
                //  if (d != null) {
                //    btn.setImageDrawable(d)
                if (!tabs.contains(bank[ind])) {
                    btn.setOnClickListener{wrongAnsOnClick(btn, questions, score)}
                } else {
                    btn.setOnClickListener {currentAnsOnClick(btn, answerBtnArr, questions, score, id_list)}
                }
                //}
            } catch (e: Exception) {
                Log.e("Error setButtonText", "Tab number: " + ind)
            }

        }
    }
    private fun currentAnsOnClick(chosen: Button,correct_ans: Array<Button>, questions: Int, score: Int, id_list: MutableList<Int> ) {
        Log.i("id size",id_list.size.toString())
        Log.i("correct ans",correct_ans.size.toString())

        for(letter in 0..id_list.size-1) {
            if(findViewById<Button>(id_list[letter]).text == chosen.text
                && findViewById<Button>(id_list[letter]).visibility == View.INVISIBLE
            ){
                Log.i("clicked","true")
                correct++
                findViewById<Button>(id_list[letter]).visibility = View.VISIBLE
                chosen.visibility = View.INVISIBLE
                break

            }
        }
        for(letter1 in 0..id_list.size-1) {
            if( findViewById<Button>(id_list[letter1]).visibility == View.INVISIBLE){
                return
            }
        }
        nextActivity(1,questions, score)

    }

    private fun wrongAnsOnClick(chosen: Button, questions: Int, score: Int) {
        chosen.visibility = View.INVISIBLE
        if(mistakes == 1) {
            nextActivity(0,questions, score)
        }
        else {
            mistakes++
        }
        //correct_ans.setBackgroundResource(R.color.Green)
        //
    }

    private fun getTabs(): Tab {


        val tab = tabsDao.getOneTab()
        var correct = 0
        //Log.i("Shuffle", "correct is: " +correct)

        return tab
    }

    private fun initDB() {
        db = Room.databaseBuilder(
            applicationContext,
            TabDataBase::class.java,
            "tabs_database"
        ).allowMainThreadQueries().build()
        tabsDao = db.tabDao
    }
    private fun letterABC(num: Int): List<Char> {
        var letter = listOf('א','ט','ק','צ','ר','ש','ת','פ','ע','ס','נ','מ','ל','כ','י','ח','ז','ו','ה','ד','ג','ב')
        letter.shuffled()

        return letter.take(num)

    }
    fun <T> merge(first: List<T>, second: List<T>): List<T> {
        return (first + second).toMutableList().shuffled()
    }

}

