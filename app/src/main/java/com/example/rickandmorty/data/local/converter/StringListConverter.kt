package com.example.rickandmorty.data.local.converter

import androidx.compose.ui.input.key.type
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object StringListConverter { // Используем object, как мы и решили
    private val gson = Gson()

    @TypeConverter
    fun fromStringList(episodeUrls: List<String>?): String? {
        return episodeUrls?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toStringList(data: String?): List<String>? {
        return data?.let {
            val listType = object : TypeToken<List<String>>() {}.type
            gson.fromJson(it, listType)
        }
    }
}