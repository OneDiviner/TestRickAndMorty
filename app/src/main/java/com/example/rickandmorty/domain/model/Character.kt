package com.example.rickandmorty.domain.model

import com.example.rickandmorty.data.network.model.LocationDto
import com.example.rickandmorty.data.network.model.OriginDto

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: OriginDto,
    val location: LocationDto,
    val image: String,
    val episodeUrls: List<String>,
    val characterUrl: String,
    val createdAt: String
)