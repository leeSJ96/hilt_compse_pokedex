package com.example.pokedex

import android.provider.SyncStateContract
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

    suspend fun fetchPokemonData(): Flow<ViewState<PokemonList>> {
        return flow {
            val comment = api.getPokemons()
            emit(ViewState.success(comment))
        }.flowOn(Dispatchers.IO)
    }
    suspend fun fetchPokemonsByOffset(offset: Int): Flow<ViewState<PokemonList>> {
        return flow {
            val comment = api.getPokemonList(limit = LIMIT_POKEMONS, offset = offset)
            emit(ViewState.success(comment))
        }.flowOn(Dispatchers.IO)
    }
}


