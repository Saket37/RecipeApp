package com.example.recipeapp.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.recipeapp.data.remote.entity.RecipeResult

@Composable
fun ProgressBar(isLoading: Boolean, recipes: List<RecipeResult>, modifier: Modifier = Modifier) {
    if (isLoading && recipes.isNotEmpty()) {
        ConstraintLayout(modifier = modifier.fillMaxSize()) {
            val progressBar = createRef()
            CircularProgressIndicator(modifier = Modifier.constrainAs(progressBar) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

        }
    }
}