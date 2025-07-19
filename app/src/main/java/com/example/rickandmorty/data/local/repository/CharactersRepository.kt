package com.example.rickandmorty.data.local.repository

import com.example.rickandmorty.data.local.dao.CharactersDao
import com.example.rickandmorty.data.local.entity.CharacterEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val charactersDao: CharactersDao,
) : ICharactersRepository {
    override suspend fun insertCharactersList(charactersList: List<CharacterEntity>) {
        charactersDao.insertCharacters(charactersList)
    }

    override fun getCharactersList(): Flow<List<CharacterEntity>> {
        return charactersDao.getAllCharacters()
    }

    override suspend fun clearAllCharacters() {
        charactersDao.clearAllCharacters()
    }

    override fun getFilteredCharacters(
        nameQuery: String?, // Можно добавить значения по умолчанию
        status: String?,
        species: String?,
        type: String?,
        gender: String?
    ): Flow<List<CharacterEntity>> {
        return charactersDao.getFilteredCharacters(nameQuery, status, species, type, gender)
    }

}