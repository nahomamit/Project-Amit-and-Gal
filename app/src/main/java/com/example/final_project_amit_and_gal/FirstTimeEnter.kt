package com.example.final_project_amit_and_gal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText


class FirstTimeEnter : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_time_enter)

        val etUsername = findViewById<TextInputEditText>(R.id.name_edit)
        val etBirth = findViewById<TextInputEditText>(R.id.birthday_edit)

        val submit = findViewById<Button>(R.id.login)
        if (submit != null && etBirth != null && etUsername != null) {
            submit.setOnClickListener {
                val username = etUsername.text.toString()
                val age = etBirth.text.toString()
                val sp = getSharedPreferences("Login", MODE_PRIVATE)
                val Ed = sp.edit()
                Ed.putString("Name", username)
                Ed.putString("Age", age)
                Ed.putBoolean("activity_executed", true)
                Ed.commit()
                val intent = Intent(this, MainMenu::class.java)
                startActivity(intent)
            }
        }


    }
}