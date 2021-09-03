package com.rain.rain.retrofit.http

import android.app.Application
import android.content.Context
import kotlin.properties.Delegates

/**
 *@Author: Rain
 *@Date: 2021/8/23 11:52
 *@Description: retrofitHttp
 */
class App : Application() {
    companion object {
        var CONTEXT: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext
        com.rain.rain.retrofit.lib.Comm.setContext(this)
    }
}