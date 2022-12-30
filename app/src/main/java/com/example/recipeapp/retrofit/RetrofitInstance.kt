package com.example.recipeapp.retrofit

import com.example.recipeapp.retrofit.service.MealAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val URL: String = "https://www.themealdb.com/api/json/v1/1/"

    /**
     * Initializing and making the call to the API
     */
    val api: MealAPI by lazy {
        Retrofit.Builder().baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create()).build().create(MealAPI::class.java)
    }
}