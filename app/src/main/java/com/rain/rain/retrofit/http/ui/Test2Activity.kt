package com.rain.rain.retrofit.http.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.rain.rain.retrofit.a.TestAActivity
import com.rain.rain.retrofit.b.TestBActivity
import com.rain.rain.retrofit.http.R
import com.rain.rain.retrofit.lib.event.GlobalEvent
import com.rain.rain.retrofit.lib.eventbus.observeEvent
import com.rain.rain.retrofit.lib.eventbus.postEvent
import com.rain.rain.retrofit.lib.eventbus.removeStickyEvent

class Test2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test2)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame, TestFragment())
            .commitAllowingStateLoss()

        observeEvent<GlobalEvent>(isSticky = true) {
            Log.i("===============", "Test2Activity from GlobalEvent isSticky = true 1 : ${it.name}")
        }

        observeEvent<GlobalEvent> {
            Log.i("===============", "Test2Activity from GlobalEvent ${it.name}")
        }

    }

    fun sendGlobal1(v: View) {
        startActivity(Intent(this, TestBActivity::class.java))
    }
    fun sendGlobal2(v: View) {
        postEvent(GlobalEvent(" send by Test2Activity "))
    }

    fun to1(v: View) {
        startActivity(Intent(this, TestAActivity::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        removeStickyEvent(GlobalEvent::class.java)
    }
}