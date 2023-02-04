package com.example.recipeapp.paging

interface Paginator<Key,Item> {
    suspend fun loadNextItems()
    fun reset()
}