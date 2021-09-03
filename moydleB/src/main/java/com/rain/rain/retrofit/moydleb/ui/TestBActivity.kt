package com.rain.rain.retrofit.moydleb.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.rain.rain.retrofit.lib.event.GlobalEvent
import com.rain.rain.retrofit.lib.eventbus.observeEvent
import com.rain.rain.retrofit.moydleb.R

/**
 *@Author: Rain
 *@Date: 2021/9/3 14:54
 *@Description: retrofitHttp
 */
class TestBActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test2)

        observeEvent<GlobalEvent>(isSticky = true) {
            Log.i("===============", "TestBActivity to GlobalEvent isSticky = true 1 ${it.name}")
        }

        observeEvent<GlobalEvent> {
            Log.i("===============", "TestBActivity to GlobalEvent 2 ${it.name}")
        }

    }
}