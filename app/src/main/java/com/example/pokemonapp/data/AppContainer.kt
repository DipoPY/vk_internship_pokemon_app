package com.example.pokemonapp.data

import com.example.pokemonapp.network.PokemonService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

interface AppContainer{
    val pokemonRepository: PokemonsRepository
}

class DefaultAppContainer: AppContainer{
    private val BASE_URL = "https://pokeapi.co/api/v2/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: PokemonService by lazy {
        retrofit.create(PokemonService::class.java)
    }

    override val pokemonRepository: PokemonsRepository by lazy {
        NetworkPokemonsRepository(retrofitService)
    }

}