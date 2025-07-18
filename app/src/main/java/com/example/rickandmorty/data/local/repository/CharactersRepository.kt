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
}