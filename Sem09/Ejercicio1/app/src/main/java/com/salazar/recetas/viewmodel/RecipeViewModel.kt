package com.salazar.recetas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.salazar.recetas.data.model.RecipeModel
import com.salazar.recetas.data.repository.RecipeRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeViewModel(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _recipes = MutableStateFlow<List<RecipeModel>>(emptyList())
    val recipes: StateFlow<List<RecipeModel>> = _recipes

    private val _selected = MutableStateFlow<RecipeModel?>(null)
    val selected: StateFlow<RecipeModel?> = _selected

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private var searchJob: Job? = null

    init {
        loadRecipes()
    }

    fun loadRecipes() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _loading.value = true
            try {
                _recipes.value = repository.getRecipes()
                _error.value = null
            } catch (e: Exception) {
                _error.value = "No se pudieron cargar las recetas"
            }
            _loading.value = false
        }
    }

    fun onQueryChange(texto: String) {
        _query.value = texto
        if (texto.isBlank()) {
            loadRecipes()
            return
        }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(400)
            _loading.value = true
            try {
                _recipes.value = repository.searchRecipes(texto.trim())
                _error.value = null
            } catch (e: Exception) {
                _error.value = "No se pudo realizar la busqueda"
            }
            _loading.value = false
        }
    }

    fun loadRecipe(id: Int) {
        viewModelScope.launch {
            _selected.value = null
            try {
                _selected.value = repository.getRecipeById(id)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "No se pudo cargar la receta"
            }
        }
    }

    class Factory(private val repository: RecipeRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            RecipeViewModel(repository) as T
    }
}
