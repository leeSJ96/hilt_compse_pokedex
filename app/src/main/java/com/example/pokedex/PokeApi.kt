package com.example.pokedex

import com.example.pokedex.util.BASE_URL
import com.example.pokedex.util.END_POINT_POKEMONS
import com.plcoding.jetpackcomposepokedex.data.remote.responses.PokemonList
import retrofit2.http.GET
import retrofit2.http.Query


interface PokeApi {

    @GET( "${BASE_URL}${END_POINT_POKEMONS}" )
    suspend fun getPokemons(): PokemonList

    @GET("pokemon/")
    suspend fun getPokemonList(
        @Query("offset") offset:Int,
        @Query("limit") limit:Int,
    ): PokemonList
}