package com.rain.rain.retrofit.lib

import androidx.room.Room
import androidx.room.RoomDatabase

inline fun <reified T:RoomDatabase> db(klass :Class<T>,name:String) : RoomDatabase.Builder<T> = Room.databaseBuilder(Comm.getApplication(),klass,name)