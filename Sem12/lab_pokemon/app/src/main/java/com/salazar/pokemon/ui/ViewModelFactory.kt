package com.salazar.pokemon.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.createSavedStateHandle
import com.salazar.pokemon.AppContainer
import com.salazar.pokemon.ui.detalle.DetalleViewModel
import com.salazar.pokemon.ui.favoritos.FavoritosViewModel
import com.salazar.pokemon.ui.lista.ListaViewModel
import com.salazar.pokemon.ui.login.LoginViewModel

class ViewModelFactory(private val container: AppContainer) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T = when {
        modelClass.isAssignableFrom(ListaViewModel::class.java) ->
            ListaViewModel(container.itemRepository) as T
        modelClass.isAssignableFrom(SesionViewModel::class.java) ->
            SesionViewModel(container.authRepository) as T
        modelClass.isAssignableFrom(LoginViewModel::class.java) ->
            LoginViewModel(container.authRepository) as T
        modelClass.isAssignableFrom(FavoritosViewModel::class.java) ->
            FavoritosViewModel(container.itemRepository) as T
        modelClass.isAssignableFrom(DetalleViewModel::class.java) -> {
            val id = extras.createSavedStateHandle().get<Int>("id") ?: error("Falta el id")
            DetalleViewModel(container.itemRepository, id) as T
        }
        else -> throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
    }
}
