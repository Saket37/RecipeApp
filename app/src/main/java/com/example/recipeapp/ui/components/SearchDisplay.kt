package com.example.recipeapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.recipeapp.R
import com.example.recipeapp.data.remote.entity.RecipeResult
import com.example.recipeapp.utils.Constants.PAGE_SIZE

enum class SearchDisplay {
    Results, NoResults, Error
}

@Composable
fun SearchResult(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    recipes: List<RecipeResult>,
    onChangeRecipeScrollPosition: (position: Int) -> Unit,
    page: Int,
    loadNextPage: () -> Unit,
    count: Int
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .wrapContentSize()
            .fillMaxWidth()
            .padding(end = 16.dp),
    ) {
        if (isLoading && recipes.isEmpty()) {
            items(8) {
                ShimmerRecipeItem()
            }
        } else {
            itemsIndexed(items = recipes) { index, recipe ->
                onChangeRecipeScrollPosition(index)
                if ((index + 1) >= ((page + 1) * PAGE_SIZE) && !isLoading) {
                    loadNextPage()
                }
                RecipeItem(recipe = recipe)
            }
        }
    }
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        ProgressBar(isLoading = isLoading, recipes = recipes, modifier = Modifier)
    }
}


@Composable
fun NoResults(
    query: String, modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize()
            .padding(24.dp)
    ) {
        Image(
            painterResource(R.drawable.empty_state_search), contentDescription = null
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.search_no_matches, query),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.search_no_matches_retry),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}