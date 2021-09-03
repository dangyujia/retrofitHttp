package com.rain.rain.retrofit.moudlea

/**
 * @ClassName: TestDao
 * @Description: java类作用描述
 * @Author: Rain
 * @Version: 1.0
 */
object ModelADao {

    private val database: AppADatabase by lazy { AppADatabase.getInstance() }

    suspend fun query(): List<ModelA> = database.modelADaoApi.query()
    fun query(userIds: LongArray): MutableList<ModelA> = database.modelADaoApi.query(userIds)
    fun query(userIds: Long): ModelA = database.modelADaoApi.query(userIds)
    suspend fun insert(vararg test: ModelA) {
        database.modelADaoApi.inserts(*test)
    }

    fun insert(test: ModelA): Long = database.modelADaoApi.insert(test)

    fun delete() {
        database.modelADaoApi.delete()
    }

    suspend fun insertAll(tests: MutableList<ModelA>) {
        database.modelADaoApi.insertAll(tests)
    }

}

object ModelA2Dao {

    private val database: AppADatabase by lazy { AppADatabase.getInstance() }

    suspend fun query(): MutableList<PinkerConfig> = database.modelA2DaoApi.query()

    suspend fun insertAll(tests: MutableList<PinkerConfig>) {
        database.modelA2DaoApi.insertAll(tests)
    }


    suspend fun delete() {
        database.modelA2DaoApi.delete()
    }
}
