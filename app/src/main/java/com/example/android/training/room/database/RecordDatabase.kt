package com.example.android.training.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.android.training.room.dao.RecordDao
import com.example.android.training.room.model.RecordInfo

@Database(entities = [RecordInfo::class], version = 1)
abstract class RecordDatabase : RoomDatabase() {

    abstract fun recordDao(): RecordDao

    companion object {

        @Volatile
        private var instance: RecordDatabase? = null

        fun getDatabase(context: Context): RecordDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    RecordDatabase::class.java,
                    "RecordInfo",
                ).fallbackToDestructiveMigration().build().also {
                    instance = it
                }
            }
        }

    }
}