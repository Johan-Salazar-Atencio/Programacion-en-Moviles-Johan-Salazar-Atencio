package com.salazar.recetas.data.remote

import com.salazar.recetas.data.model.RecipeModel
import com.salazar.recetas.data.model.RecipeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApiService {
    @GET("recipes")
    suspend fun getRecipes(@Query("limit") limit: Int = 50): RecipeResponse

    @GET("recipes/search")
    suspend fun searchRecipes(@Query("q") query: String): RecipeResponse

    @GET("recipes/{id}")
    suspend fun getRecipeById(@Path("id") id: Int): RecipeModel
}
