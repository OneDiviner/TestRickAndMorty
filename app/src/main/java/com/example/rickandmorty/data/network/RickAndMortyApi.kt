package com.example.rickandmorty.data.network

import com.example.rickandmorty.data.network.model.response.CharacterListResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("character")
    suspend fun getCharactersList(
        @Query("page") page: Int? = null, // Параметр для страницы остается
        @Query("name") name: String? = null, // Фильтр по имени
        @Query("status") status: String? = null, // Фильтр по статусу (alive, dead, unknown)
        @Query("species") species: String? = null, // Фильтр по виду
        @Query("type") type: String? = null, // Фильтр по типу
        @Query("gender") gender: String? = null // Фильтр по полу (female, male, genderless, unknown)
    ) : Response<CharacterListResponseDto>
}