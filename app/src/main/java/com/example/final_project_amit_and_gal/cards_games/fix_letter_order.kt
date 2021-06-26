package com.example.final_project_amit_and_gal.cards_games

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.LightingColorFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.room.Room
import com.example.final_project_amit_and_gal.*
import java.lang.Exception
import java.util.*

class fix_letter_order : SharedFunctions() {
    private var mistakes = 0
    private var counter = 0
    private var hint_count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fix_letter_order)
        initDB()
        //questions left

        val time:String = intent.getStringExtra("time").toString()
        val questions:Int = time.toInt()
        val time_left = findViewById<TextView>(R.id.remaining_questions)
        time_left.text = "שאלות שנותרו: " + time
        //correct ans until now
        val score:Int = intent.getStringExtra("score").toInt()
        val score_text = findViewById<TextView>(R.id.score)
        score_text.visibility = View.INVISIBLE
        score_text.text = score.toString()
        //question text
        val text = findViewById<TextView>(R.id.task_for_costumer)
        text.text = getString(R.string.game_fix_letter_order_task)

        val back = findViewById<ImageView>(R.id.return_btn)
        back.setOnClickListener {
            back_btn()
        }
        setAnswers(questions, score)
    }




    @SuppressLint("ResourceType")
    private fun setAnswers(questions: Int, score: Int) {
        var temp: Tab = getTabs()
        findViewById<TextView>(R.id.category).text=temp.subcategory
        var letters :List<Char> = temp.name.toList()
        val linear_layout = findViewById(R.id.full_word) as LinearLayout
        Log.i("array",letters.toString())

        val id_list: MutableList<Int> = mutableListOf()
        for (letter in letters) {
            val button = Button(this)
            button.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,
                1F
            )
            button.text = letter.toString()
            button.setBackgroundResource(R.drawable.category_custom_bottom)
            button.visibility = View.INVISIBLE

            button.id = View.generateViewId()
            linear_layout.addView(button)
            id_list.add(button.id)

        }
        var letter_bank =  letters.shuffled()
        val layout2 = findViewById(R.id.mixed_word) as LinearLayout
        val bank_id_list: MutableList<Int> = mutableListOf()
        for (letter in letter_bank) {
            val button = Button(this)
            button.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,
                1F
            )
            button.text = letter.toString()
            button.setBackgroundResource(R.drawable.custom_bottom)

            button.setTextColor(Color.BLUE)
            button.visibility = View.VISIBLE

            button.id = View.generateViewId()
            layout2.addView(button)
            bank_id_list.add(button.id)

        }
        Log.i("array size",letters.size.toString())

        Log.i("array size",letters.size.toString())

        setButtonText( letters,letter_bank, questions, score, id_list, bank_id_list)

    }

    private fun setButtonText(answer :List<Char>, bank :List<Char>,
                              questions: Int, score: Int, id_list: MutableList<Int>,  bank_id_list: MutableList<Int>) {
        hint(id_list, bank_id_list)
        id_list.forEachIndexed { ind, btn ->
            try {
                var button_cur =  findViewById<Button>(bank_id_list[ind])
                //  val ims: InputStream = assets.open("images/" + tabs.get(ind).url)
                button_cur.text = bank[ind].toString()
                ////val d = Drawable.createFromStream(ims, null)
                //  if (d != null) {
                //    btn.setImageDrawable(d)
                button_cur.setOnClickListener{
                    if(button_cur.text != answer[counter].toString()){
                        wrongAnsOnClick(button_cur, questions, score)
                    } else {
                        currentAnsOnClick(button_cur, questions, score, id_list)
                    }
                }
                //}
            } catch (e: Exception) {
                Log.e("Error setButtonText", "Tab number: " + ind)
            }

        }
    }
    private fun currentAnsOnClick(chosen: Button,  questions: Int, score: Int,
                                  id_list: MutableList<Int>) {
        findViewById<Button>(id_list[counter]).visibility = View.VISIBLE
        chosen.visibility = View.INVISIBLE
        counter++

        if (counter == id_list.size) {
            nextActivity(1, questions, score)
        }
    }

    private fun wrongAnsOnClick(chosen: Button, questions: Int, score: Int) {
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


        val tab = tabsDao.getOneTabWithCate()
        var correct = 0
        //Log.i("Shuffle", "correct is: " +correct)

        return tab
    }

    private fun hint(id_list:List<Int>, bank_id_list:List<Int>){

        val hint1 =  findViewById<ImageView>(R.id.hint)
        hint1.setOnClickListener{
            if(hint_count == 1 || counter == (id_list.size-1)) {
                return@setOnClickListener
            }
            hint1.setColorFilter(LightingColorFilter(Color.WHITE,Color.GRAY))
            for(btn in 0..(id_list.size-1)) {


                if(findViewById<Button>(id_list[btn]).visibility == View.INVISIBLE) {
                    findViewById<Button>(id_list[btn]).visibility = View.VISIBLE
                    for(i in 0..(bank_id_list.size-1)) {
                        if(findViewById<Button>(bank_id_list[i]).text ==  findViewById<Button>(id_list[btn]).text) {
                            findViewById<Button>(bank_id_list[i]).visibility = View.INVISIBLE
                            break
                        }
                    }
                    counter++
                    hint_count++
                    break

                }

            }
        }
    }
}