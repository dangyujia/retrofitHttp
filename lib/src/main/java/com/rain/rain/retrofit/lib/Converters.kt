package com.rain.rain.retrofit.lib

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 *@Author: Rain
 *@Date: 2021/8/26 14:40
 *@Description: retrofitHttp
 */
class Converters {
    @TypeConverter
    fun fromString(value: String): ArrayList<Int> {
        val listType = object : TypeToken<ArrayList<Int>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<Int>): String = Gson().toJson(list)
}
