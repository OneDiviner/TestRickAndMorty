package com.example.rickandmorty.data.local.repository

import com.example.rickandmorty.data.local.entity.CharacterEntity
import kotlinx.coroutines.flow.Flow

interface ICharactersRepository {

    suspend fun insertCharactersList(charactersList: List<CharacterEntity>)

    fun getCharactersList(): Flow<List<CharacterEntity>>

    suspend fun clearAllCharacters()

    fun getFilteredCharacters(
        nameQuery: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ) : Flow<List<CharacterEntity>>

}