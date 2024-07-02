package com.example.pokedex

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope

import com.example.pokedex.ui.theme.PokedexTheme
import com.plcoding.jetpackcomposepokedex.data.remote.responses.Pokemon
import com.plcoding.jetpackcomposepokedex.data.remote.responses.Result
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchPokemons()

        setContent {

            PokedexTheme {
                val viewModel = hiltViewModel<MainViewModel>()

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
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
                        Toast.makeText(this@MainActivity, "${it.message}", Toast.LENGTH_SHORT).show()
                        Log.e("PokeListFragment", it.message.toString())
                    }
                }
            }
        }
    }



}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PokedexTheme {
        Greeting("Android")
    }
}