package com.example.pokedex

import android.app.Application
import com.example.pokedex.util.Resource
import com.plcoding.jetpackcomposepokedex.data.remote.responses.PokemonList
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


class PokeRepositoryImpl @Inject constructor (
    private val api : PokeApi,
    private val appContext: Application
    ):PokeRepository{

    override suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList> {
        val response = try {
            api.getPokemonList(limit, offset)
        } catch(e: Exception) {
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(response)
    }

}