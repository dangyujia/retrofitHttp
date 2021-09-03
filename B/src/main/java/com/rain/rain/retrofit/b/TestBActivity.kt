package com.rain.rain.retrofit.b

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.rain.rain.retrofit.lib.event.GlobalEvent
import com.rain.rain.retrofit.lib.eventbus.observeEvent

class TestBActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_bactivity)


        observeEvent<GlobalEvent>(isSticky = true) {
            Log.i("===============", "TestBActivity to GlobalEvent isSticky = true 1 ${it.name}")
        }

        observeEvent<GlobalEvent> {
            Log.i("===============", "TestBActivity to GlobalEvent 2 ${it.name}")
        }

    }
}