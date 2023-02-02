package com.example.recipeapp.data.remote.entity

import com.squareup.moshi.Json

data class RecipeResult(
    @field:Json(name = "thumbnail_url") val thumbnail_url: String,
    @field:Json(name = "created_at") val created_at: Long,
    @field:Json(name = "name") val name: String,
)
