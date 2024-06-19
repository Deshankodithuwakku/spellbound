package com.example.spellbound

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class HighScore : AppCompatActivity() {
    private lateinit var highScoreTextView: TextView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var playAgainBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_high_score)
        playAgainBtn = findViewById(R.id.PlayAgainButton)
        highScoreTextView = findViewById(R.id.highScoreTextView)
        sharedPreferences = getSharedPreferences("HighScore", MODE_PRIVATE)
        loadHighScore()

        playAgainBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val btnNavigate5 = findViewById<Button>(R.id.backbutton)
        btnNavigate5.setOnClickListener {
            val intent = Intent(this, GetStart::class.java)
            startActivity(intent)
        }
    }

    private fun loadHighScore() {
        val highScore = sharedPreferences.getInt("high_score", 0)
        highScoreTextView.text = "High Score: $highScore"
    }
}
