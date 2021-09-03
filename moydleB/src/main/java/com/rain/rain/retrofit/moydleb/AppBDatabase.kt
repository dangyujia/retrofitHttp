package com.rain.rain.retrofit.moydleb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rain.rain.retrofit.lib.db

/**
 *@Author: Rain
 *@Date: 2021/8/25 10:58
 *@Description: retrofitHttp
 */
@Database(entities = [ModelB::class], version = 1,exportSchema = false)
abstract class AppBDatabase : RoomDatabase() {

    abstract val modelBDaoApi: ModelBDaoApi
    companion object {
        private var INSTANCE: AppBDatabase? = null
        fun getInstance(): AppBDatabase {
            if (INSTANCE == null) {
                synchronized(AppBDatabase::class.java) {
                    val db = db(AppBDatabase::class.java,"b_model.db")
                    if (!BuildConfig.DEBUG) {
                        //迁移数据库如果发生错误，将会重新创建数据库，而不是发生崩溃
                        db.fallbackToDestructiveMigration()
                    }
                    INSTANCE = db.build()
                }
            }
            return INSTANCE!!
        }
    }

}