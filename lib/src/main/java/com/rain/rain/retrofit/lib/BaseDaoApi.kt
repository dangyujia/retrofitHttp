package com.rain.rain.retrofit.lib

import androidx.room.*

@Dao
interface BaseDaoApi<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: MutableList<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(t: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(vararg t: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLong(list: MutableList<T>): MutableList<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLong(t: T): Long

    @Delete
    fun delete(element: T)

    @Delete
    fun deleteList(elements: MutableList<T>)

    @Delete
    fun deleteSome(vararg elements: T)

    @Update
    fun update(element: T)

    @Update
    fun update(list: MutableList<T>)
}