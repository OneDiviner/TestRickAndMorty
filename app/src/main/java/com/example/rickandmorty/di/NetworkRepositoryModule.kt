package com.example.rickandmorty.di

import com.example.rickandmorty.data.network.repository.IRickAndMortyRepository
import com.example.rickandmorty.data.network.repository.RickAndMortyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

object NetworkRepositoryModule {
    @Module
    @InstallIn(SingletonComponent::class)
    abstract class NetworkRepositoryModule {
        @Binds
        @Singleton
        abstract fun bindCourseRepository(repository: RickAndMortyRepository) : IRickAndMortyRepository
    }
}