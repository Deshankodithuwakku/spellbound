package com.example.spellbound

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Random

class MainActivity : AppCompatActivity() {
    private var wordSearch: String? = null
    private lateinit var answers: CharArray
    private var errors = 0
    private var difficulty = "Normal"
    private var score = 0
    private val letters = ArrayList<String>()
    private var image: ImageView? = null
    private var wordSpace: TextView? = null
    private var searchDisplay: TextView? = null
    private var scoreDisplay: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        image = findViewById(R.id.img)
        wordSpace = findViewById(R.id.wordSpace)
        searchDisplay = findViewById(R.id.searchDisplay)
        scoreDisplay = findViewById(R.id.scoreDisplay)
        newGame()
        val btnNavigate4 = findViewById<Button>(R.id.button5)
        btnNavigate4.setOnClickListener {
            val intent = Intent(this, GetStart::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuEasy -> difficulty = "Easy"
            R.id.menuNormal -> difficulty = "Normal"
            R.id.menuHard -> difficulty = "Hard"
            R.id.menuRandom -> difficulty = arrayOf("Easy", "Normal", "Hard").random()
        }
        newGame()
        return super.onOptionsItemSelected(item)
    }

    private fun chooseWord(): String {
        return when (difficulty) {
            "Easy" -> easyWords.random()
            "Hard" -> hardWords.random()
            else -> normalWords.random()
        }
    }

    private fun updateImage(spellbound: Int) {
        val resource = resources.getIdentifier("spellbound$spellbound", "drawable", packageName)
        image?.setImageResource(resource)
    }

    private fun newGame() {
        errors = -1
        letters.clear()
        wordSearch = chooseWord()
        answers = CharArray(wordSearch!!.length) { '_' }
        updateImage(errors)
        wordSpace?.text = stateWord()
        searchDisplay?.text = ""
    }

    private fun readLetter(c: String) {
        if (!letters.contains(c)) {
            if (wordSearch!!.contains(c)) {
                var index = wordSearch!!.indexOf(c)
                while (index >= 0) {
                    answers[index] = c[0]
                    index = wordSearch!!.indexOf(c, index + 1)
                }
            } else {
                errors++
            }
            letters.add(c)
        } else {
            Toast.makeText(this, "Letter already entered", Toast.LENGTH_SHORT).show()
        }
    }

    private fun stateWord(): String {
        return answers.joinToString(" ")
    }

    fun touchLetter(v: View) {
        if (errors < 6 && searchDisplay?.text != "YOU WIN!") {
            val letter = (v as Button).text.toString()
            readLetter(letter)
            wordSpace?.text = stateWord()
            updateImage(errors)
            if (wordSearch == String(answers)) {
                Toast.makeText(this, "YOU WIN!", Toast.LENGTH_SHORT).show()
                searchDisplay?.text = "YOU WIN!"
                when (difficulty) {
                    "Hard" -> score += 15
                    "Normal" -> score += 10
                    "Easy" -> score += 5
                }
                scoreDisplay?.text = "Score: $score"
                updateHighScore(score)
            } else {
                if (errors >= 6) {
                    updateImage(7)
                    Toast.makeText(this, "You lose...", Toast.LENGTH_SHORT).show()
                    searchDisplay?.text = wordSearch
                }
            }
        } else {
            Toast.makeText(this, "End of the game", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateHighScore(score: Int) {
        val sharedPreferences = getSharedPreferences("HighScore", Context.MODE_PRIVATE)
        val highScore = sharedPreferences.getInt("high_score", 0)
        if (score > highScore) {
            sharedPreferences.edit().putInt("high_score", score).apply()
        }
    }

    fun start(view: View?) {
        newGame()
    }




    companion object {
        private val easyWords = arrayOf("CAT", "DOG")
        private val normalWords = arrayOf("APPLE", "ORANGE")
        private val hardWords = arrayOf("ELEPHANT", "UMBRELLA")
    }
}
