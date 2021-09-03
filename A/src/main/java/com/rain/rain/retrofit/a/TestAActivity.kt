package com.rain.rain.retrofit.a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.rain.rain.retrofit.lib.event.GlobalEvent
import com.rain.rain.retrofit.lib.eventbus.observeEvent
import com.rain.rain.retrofit.lib.eventbus.postEvent

class TestAActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_aactivity)

        observeEvent<GlobalEvent>(isSticky = true) {
            Log.i("===============", "TestAActivity to GlobalEvent isSticky = true 1 ${it.name}")
        }

        observeEvent<GlobalEvent> {
            Log.i("===============", "TestAActivity to GlobalEvent 2 ${it.name}")
        }

    }

    fun sendGlobalA(v: View) {
        postEvent(GlobalEvent(" send by TestAActivity "))
    }
}