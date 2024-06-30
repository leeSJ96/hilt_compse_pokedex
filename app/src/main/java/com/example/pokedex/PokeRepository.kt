package com.example.pokedex

import com.example.pokedex.util.Resource
import com.plcoding.jetpackcomposepokedex.data.remote.responses.Pokemon
import com.plcoding.jetpackcomposepokedex.data.remote.responses.PokemonList

interface PokeRepository {
    suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList>? {
       return null
    }

    suspend fun getPokemonInfo() {

    }
}