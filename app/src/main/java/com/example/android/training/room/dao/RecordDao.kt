package com.example.android.training.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.android.training.room.model.RecordInfo
import kotlinx.coroutines.flow.Flow

/**
 *  suspend 方法里面自动切换了线程
 * */
@Dao
interface RecordDao {

    @Query("select * from recordinfo order by id desc")
    fun getAllListFlow(): Flow<List<RecordInfo>>

    @Insert
    suspend fun insert(recordInfo: RecordInfo)

    @Update
    suspend fun update(recordInfo: RecordInfo)

    @Delete
    suspend fun delete(recordInfo: RecordInfo)

    @Query("select * from recordinfo where id = :id")
    suspend fun queryById(id: Int): RecordInfo?

    @Query("delete from recordinfo")
    suspend fun deleteAll()

    @Query("select * from recordinfo order by id desc")
    suspend fun getAllRecord(): List<RecordInfo>
}