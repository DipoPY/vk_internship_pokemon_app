package com.example.pokemonapp.ui.screens

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokemonapp.ui.PokemonsViewModel

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: PokemonsViewModel =
        viewModel(factory = PokemonsViewModel.Factory)

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                pokemonsUiState = viewModel.pokemonUiState,
                retryAction = { viewModel.getPokemons(0, 100) },
                onPokemonClick = { pokemon ->
                    navController.navigate("detail/${pokemon.name}")
                }
            )
        }
        composable("detail/{pokemonName}") { backStackEntry ->
            val pokemonName =
                backStackEntry.arguments?.getString("pokemonName") ?: return@composable
            val pokemon = viewModel.getPokemonByName(pokemonName)
            pokemon?.let {
                PokemonDetailScreen(pokemon = it, onBack = { navController.popBackStack() })
            }
        }
    }
}
