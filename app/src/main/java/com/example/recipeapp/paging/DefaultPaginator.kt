package com.example.recipeapp.paging

import com.example.recipeapp.data.Resource

class DefaultPaginator<Key, Item>(
    private val initialKey: Key,
    private inline val onLoadUpdated: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextKey: Key) -> Resource<List<Item>>,
    private inline val getNextKey: suspend (List<Item>) -> Key,
    private inline val onError: suspend (Throwable?) -> Unit,
    private inline val onSuccess: suspend (items: List<Item>, newKey: Key) -> Unit
) :Paginator<Key,Item>{
    private var currentKey = initialKey
    private var isMakingRequest = false
    override suspend fun loadNextItems() {
        if(isMakingRequest){
            return
        }
        isMakingRequest = true
        onLoadUpdated(true)
        val result = onRequest(currentKey)
        isMakingRequest = false
        var items = result
    }

    override fun reset() {
        TODO("Not yet implemented")
    }
}