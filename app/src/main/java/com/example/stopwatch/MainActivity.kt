package com.example.stopwatch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton.setOnClickListener {

        }

        pauseButton.setOnClickListener {

        }

        stopButton.setOnClickListener {

        }

        resetButton.setOnClickListener {

        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
