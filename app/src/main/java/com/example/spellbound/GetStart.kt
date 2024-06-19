package com.example.spellbound

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class GetStart : AppCompatActivity() {

    lateinit var playBtn: Button
    lateinit var HighScoreBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_get_start)

        playBtn = findViewById(R.id.playButton)


        playBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

         HighScoreBtn = findViewById<Button>(R.id.HighScorebutton)
        HighScoreBtn.setOnClickListener {
            val intent = Intent(this, HighScore::class.java)
            startActivity(intent)
        }
    }
}