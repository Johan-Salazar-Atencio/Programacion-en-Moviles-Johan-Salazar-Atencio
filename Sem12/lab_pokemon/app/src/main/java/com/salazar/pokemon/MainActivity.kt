package com.salazar.pokemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.salazar.pokemon.ui.ViewModelFactory
import com.salazar.pokemon.ui.lista.ListaScreen
import com.salazar.pokemon.ui.lista.ListaViewModel
import com.salazar.pokemon.ui.theme.LabpokemonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val container = (application as PokemonApp).container
        setContent {
            LabpokemonTheme {
                val viewModel: ListaViewModel = viewModel(factory = ViewModelFactory(container))
                ListaScreen(viewModel)
            }
        }
    }
}
