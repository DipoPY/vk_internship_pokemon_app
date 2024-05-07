package com.example.pokemonapp.data

import com.example.pokemonapp.network.PokemonService
import com.example.pokemons.Results
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO


interface PokemonsRepository {
    suspend fun getPokemons(offsetQuery: Int, limitQuery: Int): List<Pokemon>
}

class NetworkPokemonsRepository(
    private val pokemonService: PokemonService
): PokemonsRepository {
    override suspend fun getPokemons(offsetQuery: Int, limitQuery: Int): List<Pokemon> = coroutineScope {
        // Получение базовой информации
        val basicPokemons = async {
            pokemonService.pokemonsSearch(offsetQuery, limitQuery).results
        }

        // Получение изображений в отдельных корутинах на диспетчере для I/O операций
        val pokemonsWithImages = basicPokemons.await().map { result ->
            async(IO) {
                val details = pokemonService.getPokemonDetail(result.url!!)
                Pokemon(
                    name = result.name,
                    url = result.url,
                    image = details.sprites?.other?.official_artwork?.frontDefault
                )
            }
        }.awaitAll()

        pokemonsWithImages
    }
}
