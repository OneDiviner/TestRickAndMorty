package com.example.rickandmorty.data.network.repository

import com.example.rickandmorty.data.network.RickAndMortyApi
import com.example.rickandmorty.data.network.model.response.CharacterListResponseDto
import javax.inject.Inject

class RickAndMortyRepository @Inject constructor(
    private val rickAndMortyApi: RickAndMortyApi
) : IRickAndMortyRepository {
    override suspend fun getCharacterList(
        page: Int?,
        characterName: String?,
        characterStatus: String?,
        characterSpecies: String?,
        characterType: String?,
        characterGender: String?
    ): Result<CharacterListResponseDto> {
        return try {
            val response = rickAndMortyApi.getCharactersList(page, characterName, characterStatus, characterSpecies, characterType, characterGender)
            Result.success(response.body() ?: throw Exception("Empty response body"))
        } catch (e: Exception) {
            Result.failure(e)
        }

    }
}