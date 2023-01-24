package com.example.recipeapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.adapters.CategoryMealsAdapter
import com.example.recipeapp.databinding.ActivityCategoryMealsBinding
import com.example.recipeapp.fragments.HomeFragment
import com.example.recipeapp.viewmodel.CategoryMealsViewModel

class CategoryMeals : AppCompatActivity() {

    lateinit var binding : ActivityCategoryMealsBinding
    lateinit var categoryMealsViewModel : CategoryMealsViewModel
     var categoryMealsAdapter = CategoryMealsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        categoryMealsViewModel = ViewModelProvider(this)[CategoryMealsViewModel::class.java]

        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        // whenever the data will change the new list will be passed to the adapter
        categoryMealsViewModel.observeMealsLiveData().observe(this, Observer { mealsList->

            mealsList.forEach{
                Log.d("test", it.strMeal)
            }

            categoryMealsAdapter.setMealsList(mealsList)
            binding.tvCategoryCount.text = mealsList.size.toString()
        })
        prepareRecylerView()
    }

    private fun prepareRecylerView(){
//        categoryMealsAdapter = CategoryMealsAdapter()
        binding.rvMealsByCategory.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        binding.rvMealsByCategory.adapter = categoryMealsAdapter

    }
}