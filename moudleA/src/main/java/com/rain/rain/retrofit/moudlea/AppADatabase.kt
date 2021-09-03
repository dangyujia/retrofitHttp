package com.rain.rain.retrofit.moudlea

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rain.rain.retrofit.lib.db

/**
 *@Author: Rain
 *@Date: 2021/8/25 10:58
 *@Description: exportSchema
 */
@Database(entities = [ModelA::class,PinkerConfig::class], version = 1, exportSchema = false)
abstract class AppADatabase : RoomDatabase() {

    abstract val modelADaoApi: ModelADaoApi
    abstract val modelA2DaoApi: ModelA2DaoApi

    companion object {
        private var INSTANCE: AppADatabase? = null
        fun getInstance(): AppADatabase {
            if (INSTANCE == null) {
                synchronized(AppADatabase::class.java) {
                    val db = db(AppADatabase::class.java,"a_model.db")
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