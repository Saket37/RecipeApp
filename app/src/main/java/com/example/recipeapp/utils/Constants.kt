package com.example.recipeapp.utils

import com.example.recipeapp.BuildConfig

object Constants {
    const val TASTY_API_BASE_URL = "https://tasty.p.rapidapi.com"
    const val HEADER_API_INTERCEPTOR = BuildConfig.RapidApiKey
    const val HEADER_HOST_INTERCEPTOR = BuildConfig.RapidApiHost
    const val PAGE_SIZE = 20
}