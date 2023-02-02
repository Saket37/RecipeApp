package com.example.recipeapp.data

import com.example.recipeapp.data.remote.TastyRecipeApiService
import com.example.recipeapp.data.remote.entity.ApiRecipeResultList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(private val recipe: TastyRecipeApiService) :
    RecipeRepository {
    override suspend fun getRecipeList(
        from: Int,
        size: Int,
        recipeName: String
    ): Flow<Resource<ApiRecipeResultList>> = flow {
        try {
            emit(Resource.Loading(true))
            val apiResponse = recipe.getRecipe(from, size, recipeName)
            emit(Resource.Success(apiResponse))
            emit(Resource.Loading(false))
        } catch (e: IOException) {
            emit(Resource.Error("IO Exception: ${e.message}"))
        } catch (e: TimeoutException) {
            emit(Resource.Error("Timeout Exception: ${e.message}"))
        } catch (e: HttpException) {
            emit(Resource.Error("Http Exception: ${e.message}"))
        }

    }
}