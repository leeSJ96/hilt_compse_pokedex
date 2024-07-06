package com.example.pokedex

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.pokedex.util.PAGE_SIZE
import com.example.pokedex.util.Resource
import com.plcoding.jetpackcomposepokedex.data.remote.responses.Pokemon
import com.plcoding.jetpackcomposepokedex.data.remote.responses.PokemonList
import com.plcoding.jetpackcomposepokedex.data.remote.responses.Result
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

    var pokemonList = mutableStateOf<List<PokedexListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    private var cachedPokemonList = listOf<PokedexListEntry>()
    private var isSearchStarting = true
    var isSearching = mutableStateOf(false)


    init {
        loadPokemonPaginated()
    }

//    private var curPage = 0
//
//
//    val pokemonsState = MutableStateFlow(
//        ViewState(
//
//            Status.LOADING,
//            PokemonList(0,"","", arrayListOf()), ""
//
//        )
//    )
//    var pokemons by mutableStateOf(Result)
//
//
//
//
//    init {
//        fetchPokemons()
//    }
//
//
//    fun fetchDataByOffset(offset: Int) {
//        pokemonsState.value = ViewState.loading()
//        viewModelScope.launch {
//
//            repository.fetchPokemonsByOffset(offset)
//                .catch {
//                    pokemonsState.value = ViewState.error(it.message.toString())
//                }
//                .collect { pokemonsResponseViewState ->
//                    pokemonsState.value = ViewState.success(pokemonsResponseViewState.data)
//                }
//        }
//    }
//    fun fetchPokemons() {
//        pokemonsState.value = ViewState.loading()
//        viewModelScope.launch {
//
//            repository.fetchPokemonData()
//                .catch {
//                    pokemonsState.value = ViewState.error(it.message.toString())
//                    Log.d("포켓몬 에러", it.message.toString())
//                }
//                .collect {
//                    pokemonsState.value = ViewState.success(it.data)
//                    Log.d("포켓몬 data", pokemonsState.value.toString())
//                }
//        }
//    }





    fun loadPokemonPaginated() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getPokemonList(PAGE_SIZE, curPage * PAGE_SIZE)
            when(result) {
                is Resource.Success -> {
                    endReached.value = curPage * PAGE_SIZE >= result.data!!.count
                    val pokedexEntries = result.data.results.mapIndexed { index, entry ->
                        val number = if(entry.url.endsWith("/")) {
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            entry.url.takeLastWhile { it.isDigit() }
                        }
                        val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                        Log.d("포켓몬 name = ", "${entry.name.capitalize(Locale.KOREA)}")

                        Log.d("포켓몬 number = ", "${number.toInt()}")
                        Log.d("포켓몬 url = ", "${url}")
                        PokedexListEntry(entry.name.capitalize(Locale.KOREA), number.toInt(),url )


                    }

                    curPage++

                    loadError.value = ""
                    isLoading.value = false
                    pokemonList.value += pokedexEntries

                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }

                is Resource.Loading -> TODO()
            }
        }
    }
    fun calcDominantColor(drawable: Drawable, onFinish: (androidx.compose.ui.graphics.Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }

}

