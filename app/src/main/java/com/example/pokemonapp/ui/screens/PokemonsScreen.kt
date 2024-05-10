package com.example.pokemonapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.pokemonapp.common.CustomFontFamily
import com.example.pokemonapp.data.Pokemon
import com.example.pokemonapp.ui.screens.help.SearchBar
import com.example.pokemonapp.ui.theme.Pink80


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PokemonsScreen(
    pokemons: List<Pokemon>,
    onPokemonClick: (Pokemon) -> Unit,
    modifier: Modifier = Modifier
) {
    val (searchQuery, setSearchQuery) = remember { mutableStateOf("") }
    val filteredPokemons = pokemons.filter {
        it.name?.contains(searchQuery, ignoreCase = true)!!
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            SearchBar(searchQuery = searchQuery, onQueryChange = setSearchQuery)
        }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(filteredPokemons) { _, pokemon ->
                PokemonsCard(
                    pokemon = pokemon,
                    onPokemonClick = onPokemonClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


@Composable
fun PokemonsCard(
    pokemon: Pokemon,
    modifier: Modifier = Modifier,
    onPokemonClick: (Pokemon) -> Unit,
) {
    Card(
        modifier = modifier
            .clickable { onPokemonClick(pokemon) }
            .padding(4.dp)
            .fillMaxWidth()
            .requiredHeight(296.dp),
        elevation = CardDefaults.cardElevation(8.dp),
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Pink80),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemon.image)
                    .crossfade(true)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )

            pokemon.name?.let {
                val name = it.first().uppercase() + it.removeRange(0..0)



                Text(
                    text = name,
                    textAlign = TextAlign.Center,
                    modifier = modifier
                        .padding(bottom = 8.dp, top = 18.dp),
                    fontSize = 27.sp,
                    fontFamily = CustomFontFamily

                )
            }
        }
    }
}



