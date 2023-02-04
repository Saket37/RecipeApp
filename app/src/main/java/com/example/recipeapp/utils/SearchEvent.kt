package com.example.recipeapp.utils

sealed class SearchEvent {
    object Observe : SearchEvent()
    data class QueryChanged(val query: String) : SearchEvent()
    data class ChangeScrollPosition(val position: Int) : SearchEvent()
    object LoadNextPage : SearchEvent()
}
