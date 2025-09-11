package com.example.android.training.room.convert

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * 类型转换器
 * 需要再实体类的对应字段添加注解：
 *  @TypeConverters(MapConverter::class)
 *  val properties: Map<String, String>
 *  需要再database中注册：
 *  @TypeConverters(MapConverter::class)
 *  abstract class AppDatabase : RoomDatabase() { ... }
 * */
class MapConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromMap(map: Map<String, String>): String {
        return gson.toJson(map)
    }

    @TypeConverter
    fun toMap(json: String): Map<String, String> {
        return gson.fromJson(json, object : TypeToken<Map<String, String>>() {}.type)
    }
}