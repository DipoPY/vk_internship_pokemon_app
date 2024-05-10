package com.example.pokemonapp.data

import com.example.pokemonapp.network.PokemonService
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope


interface PokemonsRepository {
    suspend fun getPokemons(offsetQuery: Int, limitQuery: Int): List<Pokemon>
}


class NetworkPokemonsRepository(private val pokemonService: PokemonService) : PokemonsRepository {
    private val pokemonCache = mutableMapOf<String, Pokemon>()

    override suspend fun getPokemons(offsetQuery: Int, limitQuery: Int): List<Pokemon> =
        coroutineScope {
            val results = pokemonService.pokemonsSearch(offsetQuery, limitQuery).results

            val deferreds = mutableListOf<Deferred<Pokemon>>()

            for (result in results) {
                val url = result.url
                if (url != null && !pokemonCache.containsKey(url)) {
                    val deferred = async(IO) {
                        val details = pokemonService.getPokemonDetail(url)
                        val hp = details.stats.find { it.stat?.name == "hp" }?.baseStat ?: 0
                        val attack = details.stats.find { it.stat?.name == "attack" }?.baseStat ?: 0
                        val defense =
                            details.stats.find { it.stat?.name == "defense" }?.baseStat ?: 0
                        Pokemon(
                            name = result.name,
                            url = url,
                            image = details.sprites?.other?.official_artwork?.frontDefault,
                            statHp = hp,
                            statAttack = attack,
                            statDefense = defense,
                        ).also {
                            pokemonCache[url] = it
                        }
                    }
                    deferreds.add(deferred)
                }
            }

            val newPokemons = if (deferreds.isNotEmpty()) deferreds.awaitAll() else emptyList()

            results.mapNotNull { pokemonCache[it.url] }
        }
}

