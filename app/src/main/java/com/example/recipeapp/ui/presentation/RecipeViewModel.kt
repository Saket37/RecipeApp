package com.example.recipeapp.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.data.Resource
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

    private fun observe() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = _uiState.value.copy(isLoading = true)
            withContext(Dispatchers.Main) {
                _uiState.value.query.let { query ->
                    recipeRepository.getRecipeList(
                        _uiState.value.page,
                        20,
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

    /* private val pageSize = _uiState.value.count / 20
     fun nextPage() {
         viewModelScope.launch {
             if ((recipeListScrollPosition + 1) >= (_uiState.value.page * 20)) {
                 _uiState.value = _uiState.value.copy(isLoading = true)
                 incrementPage()
                 Log.d("PAGE_VALUE", "nextPage: triggered : ${_uiState.value.page}")
                 if (_uiState.value.page > 1) {

                     _uiState.value.query?.let {
                         recipeRepository.getRecipeList(
                             _uiState.value.page, 20,
                             it
                         )
                     }?.collect { resource ->
                         when (resource) {
                             is Resource.Success -> {
                                 resource.data?.let { appendRecipes(it.results) }
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
     }*/

    private fun updateQuery(query: String) {
        _uiState.value = _uiState.value.copy(query = query)
    }

    /* private fun appendRecipes(recipe: List<RecipeResult>) {
         val current = _uiState.value.recipes
         current.addAll(recipe)
         _uiState.value = _uiState.value.copy(recipes = current)
     }

     private fun incrementPage() {
         _uiState.value.page = _uiState.value.page + 1
     }

     fun onChangeRecipeScrollPosition(position: Int) {
         recipeListScrollPosition = position
     }*/

    fun handleEvent(searchEvent: SearchEvent) {
        when (searchEvent) {
            is SearchEvent.Observe -> {
                observe()
            }
            is SearchEvent.QueryChanged -> {
                updateQuery(searchEvent.query)
            }
            /*is SearchEvent.ChangeScrollPosition -> {
                onChangeRecipeScrollPosition(searchEvent.position)
            }
            SearchEvent.LoadNextPage -> {
                nextPage()
            }*/
        }
    }
}