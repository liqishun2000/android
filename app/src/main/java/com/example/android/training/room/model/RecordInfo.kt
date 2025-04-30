package com.example.android.training.room.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey


@Keep
@Entity
data class RecordInfo(
    //自增后就不用传了
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val name: String = "",
)