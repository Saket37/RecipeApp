package com.example.recipeapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipeapp.utils.shimmerEffect

@Composable
fun ShimmerRecipeItem(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .height(250.dp)
            .padding(start = 16.dp, bottom = 16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.height(160.dp)
            ) {
                Box(
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                )
                Surface(
                    shape = CircleShape,
                    modifier = Modifier
                        .size(120.dp)
                        .align(
                            Alignment.BottomCenter
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .shimmerEffect()
                    )
                }

            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(0.8f)
                    .height(12.dp)
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(0.5f)
                    .height(12.dp)
                    .shimmerEffect()
            )
        }
    }
}


@Preview
@Composable
fun ShimmerRecipeItemPreview() {
    ShimmerRecipeItem()
}