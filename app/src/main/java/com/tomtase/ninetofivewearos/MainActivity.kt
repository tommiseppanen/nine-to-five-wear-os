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
    }

    fun setTargetTimes(startTime: Date) {
        val currentMinutes = Date().time - startTime.time
    }
}
