package com.example.pokemonapp.ui.screens.help

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun SearchBar(searchQuery: String, onQueryChange: (String) -> Unit) {
    TextField(
        value = searchQuery,
        onValueChange = onQueryChange,
        label = { Text("Search Pokemons") },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search Icon") },
        modifier = Modifier.fillMaxWidth(),
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            unfocusedTextColor = Color.LightGray,
            focusedContainerColor = Color.White,
            focusedTextColor = Color.Black,
        )
    )
}
