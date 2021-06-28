package com.example.final_project_amit_and_gal.cards_games

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.LightingColorFilter
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.final_project_amit_and_gal.*
import kotlinx.android.synthetic.main.activity_voice_reco.*
import java.io.InputStream

class VoiceReco : SharedFunctions() {

    private val RQ_SPEECH_REC = 102
    private var questions: Int = 0
    var score: Int = 0
    private var mistake_count = 0
    var tabs: Tab? = null
    private var hint_count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice_reco)
        initDB()
        //questions left
        val time:String = intent.getStringExtra("time").toString()
       questions = time.toInt()
        val time_left = findViewById<TextView>(R.id.remaining_questions)
       time_left.text = " שאלות שנותרו:" + time
        //correct ans until now
       score = intent.getStringExtra("score").toInt()
        val score_text = findViewById<TextView>(R.id.score)
        score_text.text = score.toString()
        score_text.visibility = View.INVISIBLE
        //question text
        val text = findViewById<TextView>(R.id.task_for_costumer)
       text.text = getString(R.string.game_voice_reco_task)

        val back = findViewById<ImageView>(R.id.return_btn)
       back.setOnClickListener {
            back_btn()
        }
        //    val arr_ans = correctAns()
        var talk_btn = findViewById<Button>(R.id.btn_button)
        talk_btn.setOnClickListener{
            askSpeechInput()
        }
        setAnswers()
        //text.text = current_exercize
    }





    private fun setAnswers() {
        do {
            tabs = getTab()
        } while(tabs == null)

        initImg()


    }

    private fun getTab(): Tab? {
        val tabs: Tab
        tabs = tabsDao.getOneTab()

        return tabs
    }
    private fun initImg() {
        val ims: InputStream = assets.open("images/" + tabs!!.url)
        val d = Drawable.createFromStream(ims, null)
        findViewById<ImageView>(R.id.picture).setImageDrawable(d)
        qes_text.text = tabs!!.name
        hint()
    }

    private fun currentAnsOnClick() {
        nextActivity(1,questions, score)
    }

    private fun wrongAnsOnClick() {
        if(mistake_count == 0) {
            hintClicked(findViewById(R.id.hint))
            mistake_count++
            return
        }
        nextActivity(0,questions, score)
    }





    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RQ_SPEECH_REC && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            Log.i("speak", result?.get(0).toString())
            Log.i("correct", qes_text.text as String)
            if (result?.get(0).toString() == qes_text.text.toString()) {
                tv_text.text = "Correct !"
                Log.i("ANS","CORRECT")

                currentAnsOnClick()

            }
            else{
                tv_text.text = "Wrong !"

            Log.i("ANS","FASLE")
                  wrongAnsOnClick()
        }
        }

    }

    private fun askSpeechInput() {
        if(!SpeechRecognizer.isRecognitionAvailable(this)){
            Toast.makeText(this, "Speech recogntion is not aviable", Toast.LENGTH_SHORT).show()
        } else {
            val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "he")
            i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Somathing !")
            startActivityForResult(i, RQ_SPEECH_REC)
        }
    }

    private fun hint() {
        val hint1 = findViewById<ImageView>(R.id.hint)
        hint1.setOnClickListener {
            if (hint_count == 1) {
                return@setOnClickListener
            }

          hintClicked(hint1)

        }
    }

    private fun hintClicked(hint1: ImageView) {
        hint1.setColorFilter(LightingColorFilter(Color.WHITE, Color.GRAY))
        var hint = findViewById<TextView>(R.id.hint_clicked)
        hint.visibility = View.VISIBLE
        var hint_text = getString(R.string.start_with) + " '" + qes_text.text[0] + "'\n" +
                getString(R.string.word_type) + " " + tabs!!.subcategory
        hint.text = hint_text

    }


}