package com.example.rickandmorty.domain.mapper

import com.example.rickandmorty.data.local.entity.CharacterEntity
import com.example.rickandmorty.data.network.model.CharacterDto
import com.example.rickandmorty.data.network.model.LocationDto
import com.example.rickandmorty.data.network.model.OriginDto
import com.example.rickandmorty.domain.mapper.CharacterMapper.toDomain
import com.example.rickandmorty.domain.mapper.CharacterMapper.toEntity
import com.example.rickandmorty.domain.model.Character

object CharacterMapper {

    fun CharacterDto.toEntity(): CharacterEntity {
        return CharacterEntity(
            id = id ?: 0,
            name = name,
            status = status,
            species = species,
            type = type,
            gender = gender,
            origin = origin,
            location = location,
            image = image,
            episodeUrls = episode,
            url = url,
            created = created
        )
    }

    fun CharacterEntity.toDomain() : Character {
        return Character(
            id = id,
            name = name ?: "",
            status = status ?: "",
            species = species ?: "",
            type = type ?: "",
            gender = gender ?: "",
            origin = origin ?: OriginDto("", ""),
            location = location ?: LocationDto("", ""),
            image = image ?: "",
            episodeUrls = episodeUrls ?: emptyList(),
            characterUrl = url ?: "",
            createdAt = created ?: ""
        )
    }

}

fun mapCharactersDtoListToEntityList(characterDto: List<CharacterDto>): List<CharacterEntity> {
    return characterDto.map { it.toEntity() }
}

fun mapCharactersEntityListToDomainList(characterEntity: List<CharacterEntity>): List<Character> {
    return characterEntity.map { it.toDomain() }
}