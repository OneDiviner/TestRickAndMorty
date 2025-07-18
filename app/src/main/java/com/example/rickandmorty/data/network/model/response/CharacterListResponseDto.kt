package com.example.rickandmorty.data.network.model.response

import com.example.rickandmorty.data.network.model.CharacterDto
import com.example.rickandmorty.data.network.model.InfoDto
import com.google.gson.annotations.SerializedName

data class CharacterListResponseDto(
    @SerializedName("info")
    val info: InfoDto,
    @SerializedName("results")
    val results: List<CharacterDto>
)