package com.example.recipeapp.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.databinding.ActivityMealBinding
import com.example.recipeapp.db.MealDatabase
import com.example.recipeapp.fragments.HomeFragment
import com.example.recipeapp.pojo.Meal
import com.example.recipeapp.viewmodel.MealViewModel
import com.example.recipeapp.viewmodel.MealViewModelFactory

class MealActivity : AppCompatActivity() {

    private var binding: ActivityMealBinding? = null

    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var mealVidLink: String

    private lateinit var mealMvvm: MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)
        mealMvvm = ViewModelProvider(this, viewModelFactory).get(MealViewModel::class.java)
//        mealMvvm = ViewModelProvider(this)[MealViewModel::class.java]

        getMealInfoFromIntent()
        setInfoInViews()
        loadingPhase()

        mealMvvm.getMealDetail(mealId)
        observeMealDetailsLiveData()
        onFavoriteClicked()
    }

    private fun onFavoriteClicked() {
        binding?.flBtnAddToFav?.setOnClickListener {

            mealToAdd?.let {
                mealMvvm.insertIntoFavorites(it)
                Toast.makeText(this, "Meal Added", Toast.LENGTH_SHORT)
            }
        }
    }

    private var mealToAdd: Meal? = null

    private fun observeMealDetailsLiveData() {
        mealMvvm.observeMealDetailsLiveData().observe(this, object : Observer<Meal> {
            override fun onChanged(t: Meal?) {
                responsePhase()
                val meal = t
                mealToAdd = meal
                binding?.tvCategory!!.text = "Category : ${meal!!.strCategory}"
                binding?.tvArea!!.text = "Area : ${meal!!.strArea}"
                binding?.tvInstructions!!.text = meal.strInstructions

                mealVidLink = meal.strYoutube.toString()
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

    private fun loadingPhase() {
        binding?.progressBar!!.visibility = View.VISIBLE
        binding?.flBtnAddToFav!!.visibility = View.INVISIBLE
        binding?.tvInstructions!!.visibility = View.INVISIBLE
        binding?.tvCategory!!.visibility = View.INVISIBLE
        binding?.tvArea!!.visibility = View.INVISIBLE
        binding?.ivYoutubeIcon!!.visibility = View.INVISIBLE

    }

    private fun responsePhase() {
        binding?.progressBar!!.visibility = View.INVISIBLE
        binding?.flBtnAddToFav!!.visibility = View.VISIBLE
        binding?.tvInstructions!!.visibility = View.VISIBLE
        binding?.tvCategory!!.visibility = View.VISIBLE
        binding?.tvArea!!.visibility = View.VISIBLE
        binding?.ivYoutubeIcon!!.visibility = View.VISIBLE

    }

    private fun onVideoIconClick() {
        binding?.ivYoutubeIcon!!.setOnClickListener {
            // this will automatically redirect it to the youtube app
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mealVidLink))
            startActivity(intent)
        }
    }
}