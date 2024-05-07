package com.example.pokemonapp.ui

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pokemonapp.PokemonsApplication
import com.example.pokemonapp.data.Pokemon
import com.example.pokemonapp.data.PokemonsRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface PokemonsUiState{
    data class Success(val pokemonSearch: List<Pokemon>) : PokemonsUiState
    object Error: PokemonsUiState
    object Loading: PokemonsUiState
}


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class PokemonsViewModel(private val pokemonRepository: PokemonsRepository) : ViewModel() {
    var pokemonUiState: PokemonsUiState by mutableStateOf(PokemonsUiState.Loading)
        private set

    init {
        // Загрузите первые 20 покемонов
        getPokemons(0, 100)
    }

    fun getPokemons(offset: Int, limit: Int) {
        viewModelScope.launch {
            if (pokemonUiState !is PokemonsUiState.Success) {
                pokemonUiState = PokemonsUiState.Loading
            }
            try {
                val newPokemons = pokemonRepository.getPokemons(offset, limit)
                val currentPokemons = (pokemonUiState as? PokemonsUiState.Success)?.pokemonSearch ?: emptyList()
                pokemonUiState = PokemonsUiState.Success(currentPokemons + newPokemons)
                getPokemons(100, 200)
                getPokemons(200, 500)
                getPokemons(500, 800)
                getPokemons(800, 1302)

            } catch (e: Exception) {
                pokemonUiState = PokemonsUiState.Error
            }
        }
    }





    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PokemonsApplication)
                val pokemonRepository = application.container.pokemonRepository
                PokemonsViewModel(pokemonRepository = pokemonRepository)
            }
        }
    }
}