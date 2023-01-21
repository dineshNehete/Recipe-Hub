package com.example.recipeapp.retrofit.service

import com.example.recipeapp.pojo.CategoryList
import com.example.recipeapp.pojo.PopularMealsByCategoryList
import com.example.recipeapp.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealAPI {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php?")
    fun getMealDetails(@Query("i") id: String): Call<MealList>

    @GET("filter.php?")
    fun getPopularItems(@Query("c")categoryName : String) : Call<PopularMealsByCategoryList>

    @GET("categories.php")
    fun getCategories() : Call<CategoryList>
}