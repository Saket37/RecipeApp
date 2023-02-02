package com.example.recipeapp.data.remote.entity

import com.squareup.moshi.Json

data class ApiRecipeResultList(
    @field:Json(name = "count")val count: Int,
    @field:Json(name = "results")val results: List<RecipeResult>
)