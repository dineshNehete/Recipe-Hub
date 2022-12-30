package com.example.recipeapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.recipeapp.databinding.FragmentHomeBinding
import com.example.recipeapp.pojo.Meal
import com.example.recipeapp.pojo.MealList
import com.example.recipeapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

    }

    /**
     * Following two methods are part of fragment lifecycle
     */
    override fun onCreateView(
        /**
         * good place to do any additional setup that you need for the views in your Fragment, such as setting up listeners or modifying view properties.
         */
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root!!
    }

    /**
     * Called immediately after onCreateView(LayoutInflater, ViewGroup, Bundle) has returned, but before any saved state has been restored in to the view. This gives subclasses a chance to initialize themselves once they know their view hierarchy has been completely created.
     */

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

                    Glide.with(this@HomeFragment).load(randomMeal.strMealThumb)
                        .into(binding?.ivRandomMeal!!)

                } else {
                    return;
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("Home Fragment: ", t.message.toString())
            }

        })
    }


}