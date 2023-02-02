package com.example.recipeapp.data.remote

import com.example.recipeapp.data.remote.entity.ApiRecipeResultList
import retrofit2.http.GET
import retrofit2.http.Query

interface TastyRecipeApiService {
    @GET("/recipes/list")
    suspend fun getRecipe(
        @Query("from") from: Int,
        @Query("size") size: Int,
        @Query("q") q: String
    ): ApiRecipeResultList
}