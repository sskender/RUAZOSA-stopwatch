package com.example.stopwatch

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var numberOfSeconds = 0
    private var stopwatchThread: AsyncTask<Unit, Unit, Unit>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton.setOnClickListener {
            stopwatchThread = DoInBackground()
            stopwatchThread?.execute()
        }

        pauseButton.setOnClickListener {
            stopwatchThread?.cancel(true)
        }

        resetButton.setOnClickListener {
            stopwatchThread?.cancel(true)
            resetStopwatch()
        }

    }

    private fun resetStopwatch() {
        numberOfSeconds = 0
        showProgressTextView.text = numberOfSeconds.toString()
    }

    override fun onDestroy() {
        stopwatchThread?.cancel(true)
        super.onDestroy()
    }

    inner class DoInBackground : AsyncTask<Unit, Unit, Unit>() {

        override fun onProgressUpdate(vararg values: Unit?) {
            super.onProgressUpdate(*values)

            numberOfSeconds++
            showProgressTextView.text = numberOfSeconds.toString()
        }

        override fun doInBackground(vararg params: Unit?) {
            while (true) {
                if (this.isCancelled) {
                    break
                }

                Thread.sleep(1000)
                this.publishProgress()
            }
        }

    }

}
