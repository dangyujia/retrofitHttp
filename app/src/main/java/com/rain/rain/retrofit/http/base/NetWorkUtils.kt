package com.rain.rain.retrofit.http.base

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 *@Author: Rain
 *@Date: 2021/8/23 11:02
 *@Description: 判断网络工具类
 */
object NetWorkUtils {

    private var manager: ConnectivityManager? = null

    private fun initManager(context: Context): ConnectivityManager? {
        if (manager == null) {
            manager =
                (context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?)
        } else {
            manager as ConnectivityManager
        }
        return manager
    }


    /**
     * 判断是否有网络连接
     */
    fun isNetworkAvailable(context: Context): Boolean {
        var result = false
        val manager = initManager(context)
        manager?.run {
            getNetworkCapabilities(activeNetwork)?.run {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true   //wifi
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true//数据
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true//以太网
                    else -> false
                }
            }
        }
        return result
    }

    /**
     * 判断是否是wifi下
     */
    fun isNetWifi(context: Context): Boolean {
        var result = false
        val manager = initManager(context)
        manager?.run {
            getNetworkCapabilities(activeNetwork)?.run {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    else -> false
                }
            }
        }
        return result
    }

    /**
     * 判断是否是数据流量
     */
    fun isNetCellular(context: Context): Boolean {
        var result = false
        val manager = initManager(context)
        manager?.run {
            getNetworkCapabilities(activeNetwork)?.run {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
        }
        return result
    }

    /**
     * 判读是否是以太网
     */
    fun isNetEthernet(context: Context): Boolean {
        var result = false
        val manager = initManager(context)
        manager?.run {
            getNetworkCapabilities(activeNetwork)?.run {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }
        }
        return result
    }
}