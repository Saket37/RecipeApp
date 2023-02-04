package com.example.recipeapp.ui.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.data.Resource
import com.example.recipeapp.data.remote.entity.RecipeResult
import com.example.recipeapp.utils.Constants.PAGE_SIZE
import com.example.recipeapp.utils.SearchEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val recipeRepository: RecipeRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow(RecipeState())
    val uiState get() = _uiState

    //private val page = MutableStateFlow(0)
    private var recipeListScrollPosition = 0

    init {
        observe()
    }
    // custom pagination
    private fun observe() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = _uiState.value.copy(isLoading = true,)
            withContext(Dispatchers.Main) {
                _uiState.value.query.let { query ->
                    recipeRepository.getRecipeList(
                        _uiState.value.page,
                        PAGE_SIZE,
                        query
                    )
                }
                    .collect { resource ->
                        when (resource) {
                            is Resource.Success -> {
                                val recipes = resource.data?.results
                                val count = resource.data?.count

                                recipes?.let { recipeResults ->
                                    count?.let { count ->
                                        if (recipeResults.isEmpty() && count == 0) {
                                            _uiState.value = _uiState.value.copy(
                                                isLoading = false,
                                                noResultFound = true,
                                                onObserveClicked = true,
                                                recipes = recipeResults.toMutableList(),
                                                count = count
                                            )
                                        } else {
                                            _uiState.value = _uiState.value.copy(
                                                isLoading = false,
                                                recipes = recipeResults.toMutableList(),
                                                count = count,
                                                onObserveClicked = true,
                                                noResultFound = false
                                            )
                                        }
                                    }
                                }
                            }
                            is Resource.Error -> {
                                _uiState.value =
                                    _uiState.value.copy(isLoading = false, error = resource.message)
                            }
                            is Resource.Loading -> {
                                _uiState.value = _uiState.value.copy(isLoading = resource.isLoading)
                            }
                        }
                    }
            }
        }
    }

    private fun nextPage() {
        viewModelScope.launch(Dispatchers.IO) {
            if ((recipeListScrollPosition + 1) >= ((_uiState.value.page + 1) * PAGE_SIZE)) {
                _uiState.value = _uiState.value.copy(isLoading = true)
                incrementPage()
                Log.d("PAGE_VALUE", "nextPage: triggered : ${_uiState.value.page}")
                if (_uiState.value.page > 0) {
                    withContext(Dispatchers.Main) {
                        _uiState.value.query.let {
                            recipeRepository.getRecipeList(
                                _uiState.value.page, PAGE_SIZE,
                                it
                            )
                        }.collect { resource ->
                            when (resource) {
                                is Resource.Success -> {
                                    resource.data?.let { appendRecipes(it.results) }
                                }
                                is Resource.Error -> {
                                    _uiState.value =
                                        _uiState.value.copy(
                                            isLoading = false,
                                            error = resource.message
                                        )
                                }
                                is Resource.Loading -> {
                                    _uiState.value =
                                        _uiState.value.copy(isLoading = resource.isLoading)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun updateQuery(query: String) {
        _uiState.value = _uiState.value.copy(query = query)
    }

    private fun appendRecipes(recipe: List<RecipeResult>) {
        val current = _uiState.value.recipes
        current.addAll(recipe)
        _uiState.value = _uiState.value.copy(recipes = current, isLoading = false)
    }

    private fun incrementPage() {
        _uiState.value.page = _uiState.value.page + 1
    }

    private fun onChangeRecipeScrollPosition(position: Int) {
        recipeListScrollPosition = position
    }
    // when new query is searched, clear the list to show shimmer effect
    private fun clearSearch() {
        _uiState.value = _uiState.value.copy(recipes = mutableListOf())
    }

    fun handleEvent(searchEvent: SearchEvent) {
        when (searchEvent) {
            is SearchEvent.Observe -> {
                clearSearch()
                observe()
            }
            is SearchEvent.QueryChanged -> {
                updateQuery(searchEvent.query)
            }
            is SearchEvent.ChangeScrollPosition -> {
                onChangeRecipeScrollPosition(searchEvent.position)
            }
            SearchEvent.LoadNextPage -> {
                nextPage()
            }
        }
    }
}