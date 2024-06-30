package com.example.pokedex

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository:Lazy<PokeRepositoryImpl>) :ViewModel(){

    private var curPage = 0

    var pokemonList = mutableStateOf<List<PokeData>>(listOf())
    var loadError = mutableStateOf("")
    var endReached = mutableStateOf(false)
    var isLoading = mutableStateOf(false)
    init {
        loadPokemonPaginated()
    }


    fun loadPokemonPaginated(){
        viewModelScope.launch {
            val result = repository.value.getPokemonList(PAGE_SIZE, curPage * PAGE_SIZE)
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
                        PokeData(entry.name.capitalize(Locale.KOREA),  number.toInt(),url)
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
            }

        }
    }
//    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
//        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
//
//        Palette.from(bmp).generate { palette ->
//            palette?.dominantSwatch?.rgb?.let { colorValue ->
//                onFinish(Color(colorValue))
//            }
//        }
//    }
}