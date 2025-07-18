package com.example.rickandmorty.domain.useCase

import com.example.rickandmorty.data.local.repository.ICharactersRepository
import com.example.rickandmorty.data.network.repository.IRickAndMortyRepository
import com.example.rickandmorty.domain.mapper.mapCharactersDtoListToEntityList
import javax.inject.Inject

class FetchAndSaveCharactersUseCase @Inject constructor(
    private val localRepository: ICharactersRepository,
    private val networkRepository: IRickAndMortyRepository
) {
    suspend operator fun invoke(page: Int? = null) {
        val result = networkRepository.getCharacterList(page)
        result.onSuccess { characters ->
            localRepository.insertCharactersList(mapCharactersDtoListToEntityList(characters.results))
        }
    }

}