package com.example.rickandmorty.data.network

import com.example.rickandmorty.data.network.model.response.CharacterListResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("character")
    suspend fun getCharactersList(@Query(value = "page") page: Int? = null) : Response<CharacterListResponseDto>
}