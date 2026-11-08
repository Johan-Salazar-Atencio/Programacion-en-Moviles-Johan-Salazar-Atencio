package com.salazar.pokemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.salazar.pokemon.ui.navigation.AppNav
import com.salazar.pokemon.ui.theme.LabpokemonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val container = (application as PokemonApp).container
        setContent {
            LabpokemonTheme {
                AppNav(container)
            }
        }
    }
}
