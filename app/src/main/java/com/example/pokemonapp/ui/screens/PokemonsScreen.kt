package com.example.pokemonapp.ui.screens

import android.graphics.Paint.Style
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.Coil
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.pokemonapp.R
import com.example.pokemonapp.common.CustomFontFamily
import com.example.pokemonapp.data.Pokemon
import com.example.pokemonapp.ui.theme.Pink40
import com.example.pokemonapp.ui.theme.Pink80
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import org.jetbrains.annotations.Async
import java.time.format.TextStyle


@Composable
fun PokemonsScreen(
    pokemons: List<Pokemon>,
    modifier: Modifier,
){
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        contentPadding = PaddingValues(4.dp),
        state = rememberLazyGridState().apply {
            LaunchedEffect(this) {
                snapshotFlow { layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                    .distinctUntilChanged()
            }
        }
    ) {
        itemsIndexed(pokemons) { _, pokemon ->
            PokemonsCard(pokemon = pokemon)
        }
    }

}

@Composable
fun PokemonsCard(
    pokemon : Pokemon,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier

            .padding(4.dp)
            .fillMaxWidth()
            .requiredHeight(296.dp)
            ,
        elevation = CardDefaults.cardElevation(8.dp),
    ) {

        Column(
            modifier =Modifier.fillMaxSize()
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
                modifier = Modifier.size(150.dp) // Установите размер, подходящий для вашего дизайна
            )

            pokemon.name?.let {
                val name = it.first().uppercase() + it.removeRange(0..0)
                Text(text = name,
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



