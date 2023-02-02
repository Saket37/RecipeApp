package com.example.recipeapp.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.request.RequestListener
import com.example.recipeapp.data.remote.entity.RecipeResult
import com.example.recipeapp.ui.theme.Ocean7
import com.example.recipeapp.ui.theme.Shadow5
import com.example.recipeapp.ui.theme.Shadow9
import com.example.recipeapp.utils.DateHelper.unixSecondsToText
import com.example.recipeapp.utils.offsetGradientBackground

private val HighlightCardWidth = 170.dp
private val HighlightCardPadding = 16.dp

private val gradient = listOf(Shadow5, Ocean7, Shadow9, Ocean7, Shadow5)

// The Cards show a gradient which spans 3 cards and scrolls with parallax.
private val gradientWidth
    @Composable get() = with(LocalDensity.current) {
        (2 * (HighlightCardWidth + HighlightCardPadding).toPx())
    }

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecipeItem(
    modifier: Modifier = Modifier,
    recipe: RecipeResult,
    //index: Int,
) {
    val scroll = rememberScrollState(0)
    /*val left = index * with(LocalDensity.current) {
        (HighlightCardWidth + HighlightCardPadding).toPx()
    }*/
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
                //val gradientOffset = left - (scroll.value / 3f)
                Box(
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                        .offsetGradientBackground(gradient, gradientWidth)

                )
                RecipeImage(
                    imageUrl = recipe.thumbnail_url,
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .align(
                            Alignment.BottomCenter
                        )
                )

            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = recipe.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = recipe.created_at.unixSecondsToText("dd-mm-yyyy"),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RecipeImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
) {
    Surface(color = Color.LightGray, shape = CircleShape, modifier = modifier) {
        /*GlideImage(
            model = imageUrl,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize()
        ){
            it.centerCrop()
        }*/
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(imageUrl).build(),
            contentDescription = contentDescription,
            loading = { CircularProgressIndicator() },
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun RecipeCardPreview() {
    val recipe = RecipeResult(
        thumbnail_url = "", created_at = 1, name = "Biryani"
    )
    RecipeItem(
        recipe = recipe,
    )
}