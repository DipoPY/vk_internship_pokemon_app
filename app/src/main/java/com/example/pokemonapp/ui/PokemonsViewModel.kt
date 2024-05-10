package com.example.pokemonapp.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pokemonapp.PokemonsApplication
import com.example.pokemonapp.data.Pokemon
import com.example.pokemonapp.data.PokemonsRepository
import kotlinx.coroutines.launch

sealed interface PokemonsUiState {
    data class Success(val pokemonSearch: List<Pokemon>) : PokemonsUiState
    object Error : PokemonsUiState
    object Loading : PokemonsUiState
}


class PokemonsViewModel(private val pokemonRepository: PokemonsRepository) : ViewModel() {
    var pokemonUiState: PokemonsUiState by mutableStateOf(PokemonsUiState.Loading)
    private var isCompleteLoading = false
    var searchText: String by mutableStateOf("")
    private var allPokemons: List<Pokemon> = emptyList()


    private fun updateUiState(pokemons: List<Pokemon>) {
        pokemonUiState = PokemonsUiState.Success(pokemons)
    }

    fun filterPokemons(query: String): List<Pokemon> {
        return if (query.isEmpty()) allPokemons
        else allPokemons.filter { it.name?.contains(query, ignoreCase = true)!! }
    }

    fun onSearchQueryChanged(query: String) {
        searchText = query
        updateUiState(filterPokemons(query))
    }

    init {
        getPokemons(0, 350)
    }

    fun getPokemons(offset: Int, limit: Int) {
        if (isCompleteLoading) return

        viewModelScope.launch {
            if (pokemonUiState !is PokemonsUiState.Success) {
                pokemonUiState = PokemonsUiState.Loading
            }
            try {
                allPokemons = pokemonRepository.getPokemons(offset, limit)
                updateUiState(filterPokemons(searchText))

                if (offset >= 350 && limit == 1302) {
                    isCompleteLoading = true
                } else if (!isCompleteLoading) {
                    getPokemons(0, 1302)
                }
            } catch (e: Exception) {
                pokemonUiState = PokemonsUiState.Error
            }
        }
    }

    fun getPokemonByName(name: String): Pokemon? {
        return when (val state = pokemonUiState) {
            is PokemonsUiState.Success -> state.pokemonSearch.firstOrNull { it.name == name }
            else -> null
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PokemonsApplication)
                val pokemonRepository = application.container.pokemonRepository
                PokemonsViewModel(pokemonRepository = pokemonRepository)
            }
        }
    }
}

