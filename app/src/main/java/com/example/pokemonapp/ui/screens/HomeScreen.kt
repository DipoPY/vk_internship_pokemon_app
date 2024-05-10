package com.example.pokemonapp.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.pokemonapp.data.Pokemon
import com.example.pokemonapp.ui.PokemonsUiState

@Composable
fun HomeScreen(
    pokemonsUiState: PokemonsUiState,
    retryAction: () -> Unit,
    onPokemonClick: (Pokemon) -> Unit
) {
    when (pokemonsUiState) {
        is PokemonsUiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
        is PokemonsUiState.Success -> PokemonsScreen(
            pokemons = pokemonsUiState.pokemonSearch,
            onPokemonClick = onPokemonClick
        )

        is PokemonsUiState.Error -> ErrorScreen(
            retryAction = retryAction,
            modifier = Modifier.fillMaxSize()
        )
    }
}
