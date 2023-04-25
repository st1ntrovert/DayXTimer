package com.example.dayxtimer

import androidx.appcompat.app.AppCompatActivity
import android.os.CountDownTimer
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private lateinit var countdownText: TextView
    private lateinit var countdownTimer: CountDownTimer
    private lateinit var welcomeMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        welcomeMessage = findViewById(R.id.welcomeMessage)
        countdownText = findViewById(R.id.countdownText)

        setWelcomeMessage()

        val targetDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).parse("09.07.2023")
        val currentDate = Date()
        val timeRemaining = targetDate!!.time - currentDate.time

        startCountdown(timeRemaining)
    }

    private fun setWelcomeMessage() {
        val currentTime = Calendar.getInstance()
        val currentHour = currentTime.get(Calendar.HOUR_OF_DAY)
        val greeting = when {
            currentHour in 6..12 -> "Доброе утро, буба♥"
            currentHour in 13..17 -> "Приветик, солнышко♥"
            currentHour in 18..22 -> "Добрый вечер, мадам♥"
            currentHour in 23..24 -> "спи уже, сладость"
            else -> "Чё не спишь, чудик?"
        }
        welcomeMessage.text = greeting
    }

    private fun startCountdown(timeRemaining: Long) {
        countdownTimer = object : CountDownTimer(timeRemaining, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = (millisUntilFinished / 1000) % 60
                val minutes = (millisUntilFinished / (1000 * 60)) % 60
                val hours = (millisUntilFinished / (1000 * 60 * 60)) % 24
                val days = millisUntilFinished / (1000 * 60 * 60 * 24)

                countdownText.text = String.format(
                    Locale.getDefault(),
                    "%02d дней\n%02d часов\n%02d минут\n%02d секунд",
                    days,
                    hours,
                    minutes,
                    seconds
                )
            }

            override fun onFinish() {
                countdownText.text = "Время истекло!"
            }
        }.start()
    }

    override fun onDestroy() {
        countdownTimer.cancel()
        super.onDestroy()
    }
}