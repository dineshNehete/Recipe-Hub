package com.example.recipeapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.databinding.ActivityMealBinding
import com.example.recipeapp.fragments.HomeFragment

class MealActivity : AppCompatActivity() {

    private var binding: ActivityMealBinding? = null

    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        getMealInfoFromIntent()
        setInfoInViews()
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