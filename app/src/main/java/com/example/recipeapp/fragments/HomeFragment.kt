package com.example.recipeapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.recipeapp.activities.MealActivity
import com.example.recipeapp.adapters.CategoriesAdapter
import com.example.recipeapp.adapters.PopularItemsAdapter
import com.example.recipeapp.databinding.FragmentHomeBinding
import com.example.recipeapp.pojo.Category
import com.example.recipeapp.pojo.PopularMealsByCategory
import com.example.recipeapp.pojo.Meal
import com.example.recipeapp.viewmodel.HomeViewModel


class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null

    private lateinit var homeMvvm: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemAdapter: PopularItemsAdapter
    private lateinit var categoryAdapter: CategoriesAdapter

    companion object {
        const val MEAL_ID = "com.example.recipeapp.fragments.idMeal"
        const val MEAL_NAME = "com.example.recipeapp.fragments.nameMeal"
        const val MEAL_THUMBNAIL = "com.example.recipeapp.fragments.thumbNailMeal"


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]
        popularItemAdapter = PopularItemsAdapter()
        categoryAdapter = CategoriesAdapter()
    }

    /**
     * Following two methods are part of fragment lifecycle
     */
    override fun onCreateView(
        /**
         * good place to do any additional setup that you need for the views in your Fragment, such as setting up listeners or modifying view properties.
         */
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root!!
    }

    /**
     * Called immediately after onCreateView(LayoutInflater, ViewGroup, Bundle) has returned, but before any saved state has been restored in to the view. This gives subclasses a chance to initialize themselves once they know their view hierarchy has been completely created.
     */

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemRecyclerView()

        homeMvvm.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        homeMvvm.getPopularItems()
        observerPopularItemsLiveData()
        onPopularItemClick()

        prepareCategoriesRecyclerView()
        homeMvvm.getCategories()
        observerCategoriesLiveData()

    }

    private fun prepareCategoriesRecyclerView() {
        binding?.rvCategories?.layoutManager =
            GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        binding?.rvCategories?.adapter = categoryAdapter
    }

    private fun observerCategoriesLiveData() {
        homeMvvm.observeCategoryListLiveData()
            .observe(viewLifecycleOwner, object : Observer<List<Category>> {

                override fun onChanged(categoriesList: List<Category>?) {
                    if (categoriesList != null) {
                        categoryAdapter.setCategoryList(categoriesList)
                    }
                }

            })
    }


    /**
     * When the view is created and the call is made, load the data into the image view
     */
    private fun observerRandomMeal() {
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner, object : Observer<Meal> {
            override fun onChanged(meal: Meal?) {
                if (meal != null) {
                    // loads the image into the view
                    Glide.with(this@HomeFragment).load(meal.strMealThumb)
                        .into(binding?.ivRandomMeal!!)
                    // the local variable now has all the information about the random meal fetched from the API
                    this@HomeFragment.randomMeal = meal
                }
            }

        })
    }

    /**
     * This function is called when the image holding card view is clicked.
     */
    private fun onRandomMealClick() {
        binding?.cvRandomMeal?.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            // passing on the data from this fragment to the meal activity
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMBNAIL, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    /**
     * Setting Up the Popular Items Recycler View
     */


    private fun preparePopularItemRecyclerView() {
//        binding?.rvPopularMeals?.apply<RecyclerView> {
//            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
//            adapter = popularItemAdapter
//
//        }
        binding?.rvPopularMeals?.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding?.rvPopularMeals?.adapter = popularItemAdapter
    }

    private fun observerPopularItemsLiveData() {
        homeMvvm.observePopularLiveData()
            .observe(viewLifecycleOwner, object : Observer<List<PopularMealsByCategory>> {
                override fun onChanged(mealList: List<PopularMealsByCategory>?) {
                    popularItemAdapter.setMeals(mealsList = mealList as ArrayList<PopularMealsByCategory>)
                }

            })
    }

    private fun onPopularItemClick() {
        popularItemAdapter.onItemClick = { meal ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMBNAIL, meal.strMealThumb)
            startActivity(intent)
        }
    }


}

