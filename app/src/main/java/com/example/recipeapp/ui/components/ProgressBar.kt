package com.example.recipeapp.ui.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable

@Composable
fun ProgressBar(isLoading: Boolean) {
    if (isLoading) {
        CircularProgressIndicator()
    }
}