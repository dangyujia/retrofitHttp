package com.rain.rain.retrofit.moydleb

import com.rain.rain.retrofit.lib.db

/**
 * @ClassName: TestDao
 * @Description: java类作用描述
 * @Author: Rain
 * @Version: 1.0
 */
object ModelBDao {

    private val database: AppBDatabase by lazy { AppBDatabase.getInstance()  }

    fun query(): MutableList<ModelB> = database.modelBDaoApi.query()
    fun query(userIds: LongArray): MutableList<ModelB> = database.modelBDaoApi.query(userIds)
    fun query(userIds: Long): ModelB = database.modelBDaoApi.query(userIds)
    fun insert(vararg test: ModelB) {
        database.modelBDaoApi.inserts(*test)
    }

    fun insert(test: ModelB): Long = database.modelBDaoApi.insert(test)

    fun delete() {
        database.modelBDaoApi.delete()
    }

    fun insertLong(tests: MutableList<ModelB>): MutableList<Long> =
        database.modelBDaoApi.insertLong(tests)
}