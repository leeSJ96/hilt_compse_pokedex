package com.example.pokedex

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.util.Resource
import com.plcoding.jetpackcomposepokedex.data.remote.responses.PokemonList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository:PokeRepository
) :ViewModel(){

    private var curPage = 0

    val pokemonsState = MutableStateFlow(
        ViewState(
            Status.LOADING,
            PokemonList(0,"","", arrayListOf()), ""
        )
    )


    init {
        fetchPokemons()
    }


    fun fetchDataByOffset(offset: Int) {
        pokemonsState.value = ViewState.loading()
        viewModelScope.launch {

            repository.fetchPokemonsByOffset(offset)
                .catch {
                    pokemonsState.value = ViewState.error(it.message.toString())
                }
                .collect { pokemonsResponseViewState ->
                    pokemonsState.value = ViewState.success(pokemonsResponseViewState.data)
                }
        }
    }
    fun fetchPokemons() {
        pokemonsState.value = ViewState.loading()
        viewModelScope.launch {

            repository.fetchPokemonData()
                .catch {
                    pokemonsState.value = ViewState.error(it.message.toString())
                    Log.d("포켓몬 에러", it.message.toString())
                }
                .collect {
                    pokemonsState.value = ViewState.success(it.data)

                }
        }
    }
}