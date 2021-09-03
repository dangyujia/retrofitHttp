package com.rain.rain.retrofit.moudlea.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.rain.rain.retrofit.lib.event.GlobalEvent
import com.rain.rain.retrofit.lib.eventbus.observeEvent
import com.rain.rain.retrofit.moudlea.R

/**
 *@Author: Rain
 *@Date: 2021/9/3 14:49
 *@Description: retrofitHttp
 */
class TestAActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        observeEvent<GlobalEvent>(isSticky = true) {
            Log.i("===============", "TestAActivity to GlobalEvent isSticky = true 1 ${it.name}")
        }

        observeEvent<GlobalEvent> {
            Log.i("===============", "TestAActivity to GlobalEvent 2 ${it.name}")
        }

    }
}