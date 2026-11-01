package com.salazar.recetas.data.repository

import com.salazar.recetas.data.model.RecipeModel
import com.salazar.recetas.data.remote.RecipeApiService

class RecipeRepository(
    private val apiService: RecipeApiService
) {
    suspend fun getRecipes(): List<RecipeModel> = apiService.getRecipes().recipes

    suspend fun searchRecipes(query: String): List<RecipeModel> =
        apiService.searchRecipes(query).recipes

    suspend fun getRecipeById(id: Int): RecipeModel = apiService.getRecipeById(id)
}
