package com.example.stopwatch

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var numberOfSeconds = 0
    private var stopwatchThread: AsyncTask<Unit, Unit, Unit>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resetStopwatch()

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

    fun formatStopwatchTime(secondsInput: Int) {
        val hours = secondsInput / 360
        val minutes = (secondsInput % 360) / 60
        val seconds = (secondsInput % 360) % 60

        val h = if (hours < 10) "0$hours" else "$hours"
        val m = if (minutes < 10) "0$minutes" else "$minutes"
        val s = if (seconds < 10) "0$seconds" else "$seconds"

        val time = "$h:$m:$s"

        showProgressTextView.text = time
    }

    private fun resetStopwatch() {
        numberOfSeconds = 0
        pauseButton.isEnabled = false
        resetButton.isEnabled = false
        formatStopwatchTime(numberOfSeconds)
    }

    override fun onDestroy() {
        stopwatchThread?.cancel(true)
        super.onDestroy()
    }

    inner class DoInBackground : AsyncTask<Unit, Unit, Unit>() {

        override fun onProgressUpdate(vararg values: Unit?) {
            super.onProgressUpdate(*values)

            numberOfSeconds++
            formatStopwatchTime(numberOfSeconds)
        }

        override fun doInBackground(vararg params: Unit?) {
            while (true) {
                Thread.sleep(1000)
                this.publishProgress()
            }
        }

        override fun onCancelled() {
            super.onCancelled()

            progressBar.visibility = View.INVISIBLE
            startButton.isEnabled = true
            pauseButton.isEnabled = false
        }

        /**
         * this is never executed
         *
        override fun onPostExecute(result: Unit?) {
        super.onPostExecute(result)

        progressBar.visibility = View.INVISIBLE
        }
         */

        override fun onPreExecute() {
            super.onPreExecute()

            progressBar.visibility = View.VISIBLE
            startButton.isEnabled = false
            pauseButton.isEnabled = true
            resetButton.isEnabled = true
        }

    }

}
