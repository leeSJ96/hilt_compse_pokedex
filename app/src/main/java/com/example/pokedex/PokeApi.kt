package com.example.pokedex

import com.plcoding.jetpackcomposepokedex.data.remote.responses.PokemonList
import retrofit2.http.GET
import retrofit2.http.Query


interface PokeApi {

    suspend fun getPokemons(): PokemonList
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset:Int,
        @Query("limit") limit:Int,
    ): PokemonList
}