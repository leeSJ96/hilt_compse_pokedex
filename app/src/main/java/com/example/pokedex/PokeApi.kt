package com.example.pokedex

import com.plcoding.jetpackcomposepokedex.data.remote.responses.PokemonList
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.http.GET
import retrofit2.http.Query

interface PokeApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit:Int,
        @Query("offset") offset:Int
    ): PokemonList
}