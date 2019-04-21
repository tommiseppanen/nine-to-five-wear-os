package com.tomtase.ninetofivewearos

import android.content.Context
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : WearableActivity() {

    val fileName = "start-times.txt"
    val fileTimeFormat = SimpleDateFormat("EEE d.M.yy HH:mm")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        val time = SimpleDateFormat("EEE d.M. HH:mm").format(Date())
        weekday.text = time

        val startTime = readFile()
        setTargetTimes(startTime)
    }

    private fun setTargetTimes(startTime: Date) {
        val currentMilliseconds = Date().time - startTime.time
        val minutes = currentMilliseconds/60000
        val roundedMinutes = (minutes/30)*30 //in 30min steps
        val targetTime =  Date(startTime.time + (roundedMinutes+30)*60000)
        val targetTime2 = Date(startTime.time + (roundedMinutes+60)*60000)
        val lengthInHours = roundedMinutes/60.0+0.5

        val startTimeString = SimpleDateFormat("HH:mm").format(startTime)
        val endTimeString = SimpleDateFormat("HH:mm").format(Date(startTime.time+480*60000))
        normalTime.text = "$startTimeString - $endTimeString (7.5h)"

        val target = SimpleDateFormat("HH:mm").format(targetTime)
        timeTarget.text = "$target ($lengthInHours)"

        val target2 = SimpleDateFormat("HH:mm").format(targetTime2)
        timeTarget2.text = "$target2 (${lengthInHours+0.5})"
    }

    private fun writeToFile(date: Date) {
        val entry = "${fileTimeFormat.format(date)}\n"
        try {
            val outputStream = openFileOutput(fileName, Context.MODE_APPEND or Context.MODE_PRIVATE)
            outputStream.write(entry.toByteArray())
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun readFile(): Date {
        try {
            val fis = openFileInput(fileName)
            val isr = InputStreamReader(fis)
            val bufferedReader = BufferedReader(isr)
            val sb = StringBuilder()
            var previousLine: String? = null
            var line: String? = bufferedReader.readLine()
            while (line != null) {
                sb.insert(0, "$line${System.getProperty("line.separator")}")
                previousLine = line
                line = bufferedReader.readLine()
            }
            days.text = sb.toString()

            val current = Date()
            val latest = fileTimeFormat.parse(previousLine)

            val comparisonFormat = SimpleDateFormat("yyyyMMdd")

            return if (comparisonFormat.format(current) == comparisonFormat.format(latest))
                latest
            else {
                writeToFile(current)
                current
            }

        } catch (e: FileNotFoundException) {
            val current = Date()
            writeToFile(current)
            return current
        }

        return Date()
    }
}
