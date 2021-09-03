package com.rain.rain.retrofit.lib

import android.app.Application

/**
 *@Author: Rain
 *@Date: 2021/8/25 11:01
 *@Description: retrofitHttp
 */
object Comm {
    private lateinit var application: Application

    fun setContext(app: Application) {
        application = app
    }

    fun getApplication(): Application = application
}