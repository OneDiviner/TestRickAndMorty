package com.example.rickandmorty.ui.theme.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.network.model.CharacterDto
import com.example.rickandmorty.data.network.model.InfoDto
import com.example.rickandmorty.data.network.repository.IRickAndMortyRepository
import com.example.rickandmorty.domain.model.Character
import com.example.rickandmorty.domain.useCase.FetchAndSaveCharactersUseCase
import com.example.rickandmorty.domain.useCase.GetCharactersFlowListUseCase
import com.example.rickandmorty.domain.useCase.GetFilteredCharactersFlowListUseCase
import com.example.rickandmorty.domain.useCase.LoadMoreCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class GenderFilter(val apiValue: String) {
    MALE("Male"),
    FEMALE("Female"),
    GENDERLESS("Genderless"),
    UNKNOWN("unknown");
}

//TODO: Добить филтры, сделать поиск, сделать экран песронажа, отрефакторить
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCharactersFlowListUseCase: GetCharactersFlowListUseCase,
    private val getFilteredCharactersFlowListUseCase: GetFilteredCharactersFlowListUseCase,
    private val fetchAndSaveCharactersUseCase: FetchAndSaveCharactersUseCase,
    private val loadMoreCharactersUseCase: LoadMoreCharactersUseCase
) : ViewModel() {

    var isRefreshing by mutableStateOf(false)
        private set

    private val _charactersListFlow = MutableStateFlow<List<Character>>(emptyList())
    val charactersListFlow: StateFlow<List<Character>> = _charactersListFlow.asStateFlow()

    /*private val _filteredCharactersListFlow = MutableStateFlow<List<Character>>(emptyList())
    val filteredCharactersListFlow: StateFlow<List<Character>> = _filteredCharactersListFlow.asStateFlow()*/

    @OptIn(ExperimentalCoroutinesApi::class)
    val filteredCharactersListFlow: StateFlow<List<Character>> =
        snapshotFlow { selectedGender }
            .flatMapLatest { currentGenderFilter ->
                getFilteredCharactersFlowListUseCase(gender = currentGenderFilter?.apiValue)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )


    var selectedGender by mutableStateOf<GenderFilter?>(null)

    var page by mutableIntStateOf(1)
        private set

    var isLoadingMore by mutableStateOf(false)
        private set

    var paginationInfo by mutableStateOf<InfoDto>(InfoDto.EMPTY)

    var isBottomSheetOpen by mutableStateOf(false)

    init {
        init()
        //collectCharactersList()
        //collectFilteredCharactersList()
    }

    private fun collectCharactersList() {
        viewModelScope.launch {
            getCharactersFlowListUseCase().collect { charactersList ->
                _charactersListFlow.value = charactersList
            }
        }
    }

    /*private fun collectFilteredCharactersList() {
        viewModelScope.launch {
            getFilteredCharactersFlowListUseCase(gender = selectedGender?.apiValue).collect { charactersList ->
                _filteredCharactersListFlow.value = charactersList
            }
        }
    }*/

    private fun init() {
        isRefreshing = true
        viewModelScope.launch {
            page = 1
            selectedGender = null
            paginationInfo = fetchAndSaveCharactersUseCase()
            isRefreshing = false
        }
    }
    fun refresh() {
        isRefreshing = true
        viewModelScope.launch {
            page = 1
            paginationInfo = loadMoreCharactersUseCase(page = 1, characterGender = selectedGender?.apiValue)
            isRefreshing = false
        }
    }

    fun loadMoreCharacters() {
        isLoadingMore = true
        viewModelScope.launch {
            if (paginationInfo.next != null) {
                loadMoreCharactersUseCase(page = ++page, characterGender = selectedGender?.apiValue)
            }
            isLoadingMore = false
        }
    }


}