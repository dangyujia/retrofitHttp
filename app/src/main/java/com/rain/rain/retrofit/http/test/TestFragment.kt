package com.rain.rain.retrofit.http.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

/**
 *@Author: Rain
 *@Date: 2021/9/2 15:38
 *@Description: retrofitHttp
 */
class TestFragment :Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lifecycleScope.launch {

        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}