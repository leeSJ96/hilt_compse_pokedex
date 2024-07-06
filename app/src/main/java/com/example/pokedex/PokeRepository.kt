package com.example.pokedex

import android.provider.SyncStateContract
import android.util.Log
import com.example.pokedex.util.LIMIT_POKEMONS
import com.example.pokedex.util.Resource
import com.plcoding.jetpackcomposepokedex.data.remote.responses.Pokemon
import com.plcoding.jetpackcomposepokedex.data.remote.responses.PokemonList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PokeRepository @Inject constructor(
    private val api: PokeApi
){
//    suspend fun getPokeList(limit: Int, offset: Int): PokemonList? {
//       return api.getPokemonList(limit,offset)
//    }

    suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList> {
        val response = try {
            Log.d("포켓몬 = ", "${limit}")
            Log.d("포켓몬 = ", "${offset}")
            api.getPokemonList(limit, offset)

        } catch(e: Exception) {
            Log.d("포켓몬 = ", "${e}")
            return Resource.Error("An unknown error occured.")
        }
        Log.d("포켓몬 = ", "${response}")
        return Resource.Success(response)

    }

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        val response = try {
            api.getPokemonInfo(pokemonName)

        } catch(e: Exception) {
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(response)
    }
}


