package com.example.recipeapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.databinding.ActivityMealBinding
import com.example.recipeapp.fragments.HomeFragment
import com.example.recipeapp.pojo.Meal
import com.example.recipeapp.viewmodel.MealViewModel

class MealActivity : AppCompatActivity() {

    private var binding: ActivityMealBinding? = null

    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String

    private lateinit var mealMvvm: MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        mealMvvm = ViewModelProvider(this)[MealViewModel::class.java]

        getMealInfoFromIntent()
        setInfoInViews()
        mealMvvm.getMealDetail(mealId)
        observeMealDetailsLiveData()
    }

    private fun observeMealDetailsLiveData() {
        mealMvvm.observeMealDetailsLiveData().observe(this, object : Observer<Meal> {
            override fun onChanged(t: Meal?) {
                val meal = t
                binding?.tvCategory!!.text = "Category : ${meal!!.strCategory}"
                binding?.tvArea!!.text = "Area : ${meal!!.strArea}"
                binding?.tvInstructions!!.text = meal.strInstructions
            }

        })
    }


    private fun getMealInfoFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMBNAIL)!!
    }

    private fun setInfoInViews() {
        Glide.with(applicationContext).load(mealThumb).into(binding?.ctIvMealDetail!!)

        binding?.collapsingToolbar?.title = mealName
    }
}