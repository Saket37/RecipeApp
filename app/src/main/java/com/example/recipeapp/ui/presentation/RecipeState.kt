package com.example.recipeapp.ui.presentation

import com.example.recipeapp.data.remote.entity.RecipeResult

data class RecipeState(
    val query: String? = null,
    val count: Int = 0,
    var recipes: MutableList<RecipeResult> = mutableListOf(),
    val isLoading: Boolean = false,
    val error: String? = null,
    var page: Int = 0,
    val onObserveClicked: Boolean = false,
    val noResultFound: Boolean = false
)
