package com.example.pokedex

import com.example.pokedex.util.BASE_URL
import com.example.pokedex.util.END_POINT_POKEMONS
import com.plcoding.jetpackcomposepokedex.data.remote.responses.Pokemon
import com.plcoding.jetpackcomposepokedex.data.remote.responses.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PokeApi {

    @GET( "${BASE_URL}${END_POINT_POKEMONS}" )
    suspend fun getPokemons(): PokemonList



    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonList

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(
        @Path("name") name: String
    ): Pokemon
}