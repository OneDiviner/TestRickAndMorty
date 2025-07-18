package com.example.rickandmorty.data.network.repository

import com.example.rickandmorty.data.network.model.response.CharacterListResponseDto
import retrofit2.Response

interface IRickAndMortyRepository {
    suspend fun getCharacterList(page: Int? = null) : Result<CharacterListResponseDto>
}