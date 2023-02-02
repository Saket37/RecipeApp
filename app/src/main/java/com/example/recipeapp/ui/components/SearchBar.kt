package com.example.recipeapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipeapp.R
import com.example.recipeapp.ui.theme.FunctionalDarkGrey

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    query: String?,
    onQueryChange: (query: String) -> Unit,
    onSearchClick: () -> Unit,
    keyboardController: SoftwareKeyboardController?
) {
    Surface(
        color = FunctionalDarkGrey,
        shape = MaterialTheme.shapes.small,
        contentColor = Color.White,
        modifier = modifier
            .height(56.dp)
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .clip(shape = CircleShape)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (query == null || query.isEmpty()) {
                SearchHint()
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize()
            ) {
                BasicTextField(
                    value = query ?: "",
                    onValueChange = { onQueryChange(it) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 6.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(onSearch = {
                        if (query != null) {
                            keyboardController?.hide()
                            onSearchClick()
                        }
                    }),
                    textStyle = TextStyle(color = Color.White, fontSize = 18.sp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Button(onClick = {
                    keyboardController?.hide()
                    onSearchClick()
                }) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = stringResource(id = R.string.label_search)
                    )
                }
            }
        }
    }
}

@Composable
fun SearchHint() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()
    ) {
        Icon(
            imageVector = Icons.Outlined.Search,
            contentDescription = stringResource(id = R.string.label_search)
        )
        Spacer(Modifier.width(8.dp))
        Text(text = stringResource(id = R.string.search_recipe))
    }
}