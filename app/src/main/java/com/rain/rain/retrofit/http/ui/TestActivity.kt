package com.rain.rain.retrofit.http.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.rain.rain.retrofit.http.R
import com.rain.rain.retrofit.lib.event.GlobalEvent
import com.rain.rain.retrofit.lib.eventbus.postEvent

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }

    fun sendGlobal(v: View) {
        postEvent(GlobalEvent(" send by TestActivity "))
    }

    fun sendActivity(v: View) {

    }

    fun to(v: View) {
        startActivity(Intent(this, Test2Activity::class.java))
    }

}