package com.example.recipeapp.di

import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.data.RecipeRepositoryImpl
import com.example.recipeapp.data.remote.TastyRecipeApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideRecipeRepository(recipeApiService: TastyRecipeApiService): RecipeRepository {
        return RecipeRepositoryImpl(recipeApiService)
    }
}