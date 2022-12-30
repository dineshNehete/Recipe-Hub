package com.example.recipeapp.retrofit.service

import com.example.recipeapp.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET

interface MealAPI {

    @GET("random.php")
    fun getRandomMeal() : Call<MealList>
}