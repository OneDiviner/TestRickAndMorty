package com.example.rickandmorty.di

import com.example.rickandmorty.data.local.repository.CharactersRepository
import com.example.rickandmorty.data.local.repository.ICharactersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

object LocalRepositoryModule {
    @Module
    @InstallIn(SingletonComponent::class)
    abstract class LocalRepositoryModule {
        @Binds
        @Singleton
        abstract fun bindCharactersRepository(repository: CharactersRepository) : ICharactersRepository
    }
}