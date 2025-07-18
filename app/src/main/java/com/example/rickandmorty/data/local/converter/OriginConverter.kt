package com.example.rickandmorty.data.local.converter

import androidx.room.TypeConverter
import com.example.rickandmorty.data.network.model.OriginDto
import com.google.gson.Gson

object OriginConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromOriginDto(originDto: OriginDto?): String? {
        return originDto?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toOriginDto(data: String?): OriginDto? {
        return data?.let { gson.fromJson(it, OriginDto::class.java) }
    }
}