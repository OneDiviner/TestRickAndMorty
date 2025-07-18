package com.example.rickandmorty.data.local.converter

import androidx.room.TypeConverter
import com.example.rickandmorty.data.network.model.LocationDto
import com.google.gson.Gson

object LocationConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromLocationDto(location: LocationDto?): String? {
        return location?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toLocationDto(data: String?): LocationDto? {
        return data?.let { gson.fromJson(it, LocationDto::class.java) }
    }
}