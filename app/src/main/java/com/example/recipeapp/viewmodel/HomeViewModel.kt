package com.example.recipeapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.pojo.*
import com.example.recipeapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel() : ViewModel() {
    /**
     * Mutable because its value will change everytime the screen is loaded.
     */
    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<PopularMealsByCategory>>()
    private var categoryListLiveData = MutableLiveData<List<Category>>()
    fun getRandomMeal() {
        /**
         * Establishing the connection
         */
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                /**
                 * If value is present then the api will return a list of Meal,
                 * It always return only one value in the list, therefore index is 0
                 */
                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    Log.d("TEST", "meal id ${randomMeal.idMeal} name ${randomMeal.strMeal}")
                    randomMealLiveData.value = randomMeal

                } else {
                    return;
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("Home Fragment: ", t.message.toString())
            }

        })
    }

    /**
     * Function to help listen to live data from the HomeFragment
     * Once we get the value we don't want to change the value from outside of the class, therefore returning LiveData instance
     */
    fun observeRandomMealLiveData(): LiveData<Meal> {
        return randomMealLiveData
    }

    fun getPopularItems() {
        RetrofitInstance.api.getPopularItems("Seafood")
            .enqueue(object : Callback<PopularMealsByCategoryList> {
                override fun onResponse(
                    call: Call<PopularMealsByCategoryList>,
                    response: Response<PopularMealsByCategoryList>,
                ) {
                    if (response.body() != null) {
                        popularItemsLiveData.value = response.body()!!.meals
                    }
                }

                override fun onFailure(call: Call<PopularMealsByCategoryList>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }

    fun observePopularLiveData(): LiveData<List<PopularMealsByCategory>> {
        return popularItemsLiveData
    }


    fun getCategories() {
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if (response.body() != null) {
                    categoryListLiveData.value = response.body()?.categories
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.e("Category loading failed", t.message.toString())
            }

        })
    }

    fun observeCategoryListLiveData(): LiveData<List<Category>> {
        return categoryListLiveData
    }


}