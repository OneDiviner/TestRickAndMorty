package com.example.rickandmorty.domain.useCase

import com.example.rickandmorty.data.local.repository.ICharactersRepository
import com.example.rickandmorty.domain.mapper.mapCharactersEntityListToDomainList
import com.example.rickandmorty.domain.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCharactersFlowListUseCase @Inject constructor(
    private val localRepository: ICharactersRepository
) {
    operator fun invoke(page: Int? = null) : Flow<List<Character>> {
        return localRepository.getCharactersList().map { characterEntityList ->
            mapCharactersEntityListToDomainList(characterEntityList)
        }
    }
}