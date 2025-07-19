package com.example.rickandmorty.domain.useCase

import com.example.rickandmorty.data.local.repository.ICharactersRepository
import com.example.rickandmorty.data.network.model.InfoDto
import com.example.rickandmorty.data.network.repository.IRickAndMortyRepository
import com.example.rickandmorty.domain.mapper.mapCharactersDtoListToEntityList
import javax.inject.Inject


class LoadMoreCharactersUseCase @Inject constructor(
    private val localRepository: ICharactersRepository,
    private val networkRepository: IRickAndMortyRepository
) {
    suspend operator fun invoke(
        page: Int,
        characterName: String? = null,
        characterStatus: String? = null,
        characterSpecies: String? = null,
        characterType: String? = null,
        characterGender: String? = null
    ) : InfoDto {
        val result = networkRepository.getCharacterList(page, characterName, characterStatus, characterSpecies, characterType, characterGender)
        result.onSuccess { characters ->
            localRepository.insertCharactersList(mapCharactersDtoListToEntityList(characters.results))
            return characters.info
        }
        return InfoDto.EMPTY
    }

}