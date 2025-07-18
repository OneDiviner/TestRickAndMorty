package com.example.rickandmorty.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.rickandmorty.data.local.converter.LocationConverter
import com.example.rickandmorty.data.local.converter.OriginConverter
import com.example.rickandmorty.data.local.converter.StringListConverter
import com.example.rickandmorty.data.network.model.LocationDto
import com.example.rickandmorty.data.network.model.OriginDto

@Entity(tableName = "characters")
@TypeConverters(
    StringListConverter::class,
    OriginConverter::class,
    LocationConverter::class
)
data class CharacterEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "status")
    val status: String?,

    @ColumnInfo(name = "species")
    val species: String?,

    @ColumnInfo(name = "type")
    val type: String?,

    @ColumnInfo(name = "gender")
    val gender: String?,

    @ColumnInfo(name = "origin")
    val origin: OriginDto?,

    @ColumnInfo(name = "location")
    val location: LocationDto?,

    @ColumnInfo(name = "image_url")
    val image: String?,

    @ColumnInfo(name = "episode_urls")
    val episodeUrls: List<String>?,

    @ColumnInfo(name = "character_url")
    val url: String?,

    @ColumnInfo(name = "created_at")
    val created: String?
)