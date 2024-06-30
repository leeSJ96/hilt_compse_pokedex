package com.example.pokedex

import com.example.pokedex.util.Resource
import com.plcoding.jetpackcomposepokedex.data.remote.responses.Pokemon
import com.plcoding.jetpackcomposepokedex.data.remote.responses.PokemonList
import javax.inject.Inject

class PokeRepository @Inject constructor(
    private val api: PokeApi
){
    suspend fun getPokeList(limit: Int, offset: Int): Resource<PokemonList>? {
       return api.getPokemonList(limit,offset)
    }


}