package com.example.rickandmorty.domain.useCase

import com.example.rickandmorty.data.local.repository.ICharactersRepository
import com.example.rickandmorty.domain.mapper.mapCharactersEntityListToDomainList
import com.example.rickandmorty.domain.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFilteredCharactersFlowListUseCase @Inject constructor(
    private val localRepository: ICharactersRepository
) {
    operator fun invoke(
        nameQuery: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ) : Flow<List<Character>> {
        return localRepository.getFilteredCharacters(nameQuery, status, species, type, gender).map { characterEntityList ->
            mapCharactersEntityListToDomainList(characterEntityList)
        }
    }
}