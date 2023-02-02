package com.example.recipeapp.di

import com.example.recipeapp.BuildConfig
import com.example.recipeapp.data.remote.TastyRecipeApiService
import com.example.recipeapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideApiService(client: OkHttpClient): TastyRecipeApiService {
        return Retrofit.Builder().baseUrl(Constants.TASTY_API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create()).client(client).build()
            .create(TastyRecipeApiService::class.java)
    }

    @Provides
    fun provideClientBuilder(): OkHttpClient {
        val logInterceptor = HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        }
        val headerAPIInterceptor = Interceptor {
            val request =
                it.request().newBuilder().addHeader(
                    "X-RapidAPI-Key",
                    Constants.HEADER_API_INTERCEPTOR
                )
                    .build()
            it.proceed(request)
        }
        val headerHostInterceptor = Interceptor {
            val request =
                it.request().newBuilder().addHeader("X-RapidAPI-Host", Constants.HEADER_HOST_INTERCEPTOR)
                    .build()
            it.proceed(request)
        }
        val httpClient = OkHttpClient.Builder().apply {
            addInterceptor(logInterceptor)
            addInterceptor(headerAPIInterceptor)
            addInterceptor(headerHostInterceptor)
            connectTimeout(2, TimeUnit.MINUTES)
            readTimeout(2, TimeUnit.MINUTES)
            writeTimeout(4, TimeUnit.MINUTES)
        }
        return httpClient.build()
    }

}