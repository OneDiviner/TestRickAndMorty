package com.example.rickandmorty.ui.theme.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.network.model.CharacterDto
import com.example.rickandmorty.data.network.repository.IRickAndMortyRepository
import com.example.rickandmorty.domain.model.Character
import com.example.rickandmorty.domain.useCase.FetchAndSaveCharactersUseCase
import com.example.rickandmorty.domain.useCase.GetCharactersFlowListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCharactersFlowListUseCase: GetCharactersFlowListUseCase,
    private val fetchAndSaveCharactersUseCase: FetchAndSaveCharactersUseCase
) : ViewModel() {

    var isRefreshing by mutableStateOf(false)
        private set

    private val _charactersListFlow = MutableStateFlow<List<Character>>(emptyList())
    val charactersListFlow: StateFlow<List<Character>> = _charactersListFlow.asStateFlow()

    init {
        refresh()
        collectCharactersList()
    }

    private fun collectCharactersList() {
        viewModelScope.launch {
            getCharactersFlowListUseCase().collect { charactersList ->
                _charactersListFlow.value = charactersList
            }
        }
    }

    fun refresh() {
        isRefreshing = true
        viewModelScope.launch {
            fetchAndSaveCharactersUseCase()
            isRefreshing = false
        }
    }

}