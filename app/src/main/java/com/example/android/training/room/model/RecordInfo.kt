package com.example.android.training.room.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.android.training.room.convert.MapConverter


@Keep
@Entity
data class RecordInfo(
    //自增后就不用传了
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    @ColumnInfo(name = "name") val name: String = "",
    @TypeConverters(MapConverter::class)
    val map: Map<String, String> = mutableMapOf()
)