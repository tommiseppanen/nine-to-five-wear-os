package com.tomtase.ninetofivewearos

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class MainActivity : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enables Always-on
        setAmbientEnabled()

        val time = SimpleDateFormat("EEE d.M.").format(Date())
        weekday.text = time

        setTargetTimes(Date())
    }

    fun setTargetTimes(startTime: Date) {
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
}
