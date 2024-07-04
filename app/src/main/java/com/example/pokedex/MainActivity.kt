package com.example.pokedex

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import com.plcoding.jetpackcomposepokedex.data.remote.responses.Result
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

import com.example.pokedex.ui.theme.PokedexTheme
import com.plcoding.jetpackcomposepokedex.data.remote.responses.Pokemon
import com.plcoding.jetpackcomposepokedex.data.remote.responses.PokemonList
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeData()
        viewModel.fetchPokemons()

        setContent {

            PokedexTheme {


                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(viewModel)
                }
            }
        }
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.pokemonsState.collect {
                when (it.status) {
                    Status.LOADING -> {

                    }

                    Status.SUCCESS -> {

                        it.data?.let { pokeResponse ->
                            val list: List<Result> = pokeResponse.results!!.map { singlePokemon ->
                                Result(
                                    name = singlePokemon.name,
                                    url = singlePokemon.url,
                                )

                            }

//                            if (offset <= 0) {
//                                binding.apply {
//                                    btnLeft.isEnabled = false
//                                }
//                            } else {
//                                binding.btnLeft.isEnabled = true
//                            }


//                            binding.recycler.adapter = PokeListAdapter(list) { pokemon ->
//                                val bundle = bundleOf("name" to pokemon.name)
//                                view?.findNavController()
//                                    ?.navigate(R.id.action_listFragment_to_detailFragment, bundle)
//                            }
                            Log.d("포켓몬", "Received poke list.")
                        }
                            ?: run {
                                Log.e("포켓몬", "Error: Failed to fetch poke list.")
                            }
                    }

                    // error occurred status
                    else -> {
                        Toast.makeText(this@MainActivity, "${it.message}", Toast.LENGTH_SHORT)
                            .show()
                        Log.e("PokeListFragment", it.message.toString())
                    }
                }
            }
        }
    }


}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CustomItem(
    pokemon: com.plcoding.jetpackcomposepokedex.data.remote.responses.Result,
    modifier: Modifier
) {
    Row(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxWidth()
            .padding(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "${pokemon.name}",
            color = Color.Black,
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold
        )
        GlideImage(
            model = pokemon.url,
            contentDescription = "loadingImage",
            modifier = modifier.fillMaxWidth()
        ) {
            it.error(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.ic_launcher_background)
                .load(pokemon.url)
        }


    }
}


//@Composable
//@Preview
//fun CustomItemPreview() {
//    CustomItem(
//        result = Result(
//            name = 0,
//            src = "John",
//        )
//    )
//}


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Greeting(viewModel: MainViewModel) {
    var pokemonList  = viewModel.pokemonsState.value.data?.results
    Log.d("check pokemonList =", pokemonList.toString())
    LazyColumn {
        items(pokemonList?.size?.minus(1) ?: 0) { it ->
            pokemonList?.get(it)?.let { it1 -> CustomItem(pokemon = it1, modifier = Modifier) }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PokedexTheme {

    }
}