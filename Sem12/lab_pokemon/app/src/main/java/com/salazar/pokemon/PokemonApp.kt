package com.salazar.pokemon

import android.app.Application

class PokemonApp : Application() {
    val container by lazy { AppContainer() }
}
