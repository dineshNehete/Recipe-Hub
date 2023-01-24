package com.example.recipeapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.pojo.PopularMealsByCategory
import com.example.recipeapp.pojo.PopularMealsByCategoryList
import com.example.recipeapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel : ViewModel() {

    val mealsLiveData = MutableLiveData<List<PopularMealsByCategory>>()

    fun getMealsByCategory(categoryName : String) {
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object : Callback<PopularMealsByCategoryList>{
            override fun onResponse(
                call: Call<PopularMealsByCategoryList>,
                response: Response<PopularMealsByCategoryList>,
            ) {
                response.body()?.let { mealsList->
                    mealsLiveData.postValue(mealsList.meals)
                }

            }

            override fun onFailure(call: Call<PopularMealsByCategoryList>, t: Throwable) {
                Log.e("CategoryViewModel", t.message.toString())
            }

        })
    }

    fun observeMealsLiveData() : LiveData<List<PopularMealsByCategory>> {
        return mealsLiveData
    }
}