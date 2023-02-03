package com.example.recipeapp.ui.presentation

import com.example.recipeapp.data.remote.entity.RecipeResult
import com.example.recipeapp.ui.components.SearchDisplay

data class RecipeState(
    val query: String = "",
    val count: Int = 0,
    var recipes: MutableList<RecipeResult> = mutableListOf(),
    val isLoading: Boolean = false,
    val error: String? = null,
    var page: Int = 0,
    var onObserveClicked: Boolean = false,
    var noResultFound: Boolean = false,
) {
    //TODO when query is "" if noresults is displayed should call the api
    val searchDisplay: SearchDisplay
        get() = when {
            !isLoading && recipes.isEmpty() && noResultFound &&
                    onObserveClicked -> SearchDisplay.NoResults
            !isLoading && !error.isNullOrEmpty() -> SearchDisplay.Error
            else -> {
                SearchDisplay.Results
            }
        }

}
