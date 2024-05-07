package com.example.pokemonapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.pokemonapp.ui.PokemonsUiState

@Composable
fun HomeScreen(
    pokemonsUiState: PokemonsUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (pokemonsUiState) {
        is PokemonsUiState.Loading -> LoadingScreen(modifier)
        is PokemonsUiState.Success -> PokemonsScreen(
            pokemons = pokemonsUiState.pokemonSearch,
            modifier = modifier
        )
        is PokemonsUiState.Error -> ErrorScreen(retryAction = retryAction, modifier = modifier)
    }
}


@Composable
fun EmptyScreen(modifier: Modifier) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("No Pokemons found")
    }
}
