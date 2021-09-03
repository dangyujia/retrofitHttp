package com.rain.rain.retrofit.http.test

import android.os.Parcelable
import com.rain.rain.retrofit.moudlea.PinkerConfig

/**
 *@Author: Rain
 *@Date: 2021/8/24 9:31
 *@Description: retrofitHttp
 */
data class UserData(
    val uId: String,
    val rongToken: String,
    val Authorization: String,
    val uBh: String
)

data class A(val id: Int = 0, val name: String = "", val list: MutableList<C> = arrayListOf())
data class B(val id: Int = 0, val name: String = "", val list: MutableList<C> = arrayListOf())
data class C(val name: String)

//
//data class PinkerConfig(
//    val id: Int,
//    val name: String,
//    val price: String
//)

data class PinkerData(
    val pinkerNum: String? = null, //拼团数
    val waitWinNum: String? = null, //待开奖
    val winNum: String? = null, //已开奖
    val winFailNum: String? = null, //开团失败
    val joinNum: String? = null,//参与次数
    val joinPersonNum: String? = null//参与人数
)

data class Pinker(val list: MutableList<PinkerConfig> = arrayListOf(), val data: PinkerData? = null)


class KResult<T> {
    val code: Int = 0
    val flag: Boolean = false
    val message: String = ""
    val data: T? = null

    override fun toString(): String {
        return "Result(code=$code, flag=$flag, message='$message', data=$data)"
    }

}