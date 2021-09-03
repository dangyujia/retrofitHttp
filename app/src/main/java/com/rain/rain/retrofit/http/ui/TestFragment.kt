package com.rain.rain.retrofit.http.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rain.rain.retrofit.http.R
import com.rain.rain.retrofit.lib.event.GlobalEvent
import com.rain.rain.retrofit.lib.eventbus.observeEvent

/**
 *@Author: Rain
 *@Date: 2021/9/3 15:03
 *@Description: retrofitHttp
 */
class TestFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_test, null)
        initView(v)
        return v
    }

    private fun initView(v: View?) {
        v?.apply {

        }

        observeEvent<GlobalEvent>(isSticky = true) {
            Log.i("===============", "TestFragment to GlobalEvent isSticky = true 1 ${it.name}")
        }

        observeEvent<GlobalEvent> {
            Log.i("===============", "TestFragment to GlobalEvent 2 ${it.name}")
        }

    }
}