package com.example.recipeapp.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.recipeapp.ui.components.*
import com.example.recipeapp.ui.presentation.RecipeState
import com.example.recipeapp.ui.presentation.RecipeViewModel
import com.example.recipeapp.utils.SearchEvent

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun HomeScreen() {
    val viewModel: RecipeViewModel = hiltViewModel()
    HomeContent(
        uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
        handleEvent = viewModel::handleEvent,
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    uiState: RecipeState,
    handleEvent: (event: SearchEvent) -> Unit,

    ) {
    Column {
        Spacer(modifier = Modifier.statusBarsPadding())
        val searchFocusRequester = FocusRequester()
        val keyboardController = LocalSoftwareKeyboardController.current
        // TODO clear focus when search is clicked
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(searchFocusRequester),
            query = uiState.query,
            onQueryChange = { handleEvent(SearchEvent.QueryChanged(it)) },
            onSearchClick = {
                handleEvent(SearchEvent.Observe)
            },
            keyboardController = keyboardController
        )
        Spacer(modifier = Modifier.height(12.dp))
        Box(modifier = Modifier.fillMaxSize()) {
            // TODO if any error occurred
            when (uiState.searchDisplay) {
                SearchDisplay.NoResults -> NoResults(query = uiState.query)
                SearchDisplay.Results -> {
                    SearchResult(isLoading = uiState.isLoading, recipes = uiState.recipes)
                }
                SearchDisplay.Error -> {

                }
            }
        }
    }

}