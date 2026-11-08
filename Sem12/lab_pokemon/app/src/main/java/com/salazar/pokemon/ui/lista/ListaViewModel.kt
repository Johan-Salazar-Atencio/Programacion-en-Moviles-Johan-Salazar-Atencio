package com.salazar.pokemon.ui.lista

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.pokemon.data.repository.ItemRepository
import com.salazar.pokemon.domain.model.Item
import com.salazar.pokemon.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListaViewModel(private val repo: ItemRepository) : ViewModel() {

    private val _estado = MutableStateFlow<UiState<List<Item>>>(UiState.Cargando)
    val estado: StateFlow<UiState<List<Item>>> = _estado.asStateFlow()

    init {
        viewModelScope.launch {
            repo.items.collect { _estado.value = UiState.Exito(it) }
        }
    }
}
