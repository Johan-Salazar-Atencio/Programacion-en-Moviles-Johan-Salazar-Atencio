package com.salazar.pokemon.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.salazar.pokemon.AppContainer
import com.salazar.pokemon.ui.lista.ListaViewModel

class ViewModelFactory(private val container: AppContainer) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(ListaViewModel::class.java) ->
            ListaViewModel(container.itemRepository) as T
        else -> throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
    }
}
