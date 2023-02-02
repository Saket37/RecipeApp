package com.example.recipeapp.data

import com.example.recipeapp.data.remote.entity.ApiRecipeResultList
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun getRecipeList(
        from: Int,
        size: Int,
        recipeName: String
    ): Flow<Resource<ApiRecipeResultList>>
}