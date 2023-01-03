package com.example.recipeapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.recipeapp.activities.MealActivity
import com.example.recipeapp.databinding.FragmentHomeBinding
import com.example.recipeapp.pojo.Meal
import com.example.recipeapp.viewmodel.HomeViewModel


class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null

    private lateinit var homeMvvm: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]
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
        homeMvvm.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()
    }

    private fun onRandomMealClick() {
        binding?.cvRandomMeal?.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            startActivity(intent)
        }
    }


    /**
     * When the view is created and the call is made, load the data into the image view
     */
    private fun observerRandomMeal() {
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner, object : Observer<Meal> {
            override fun onChanged(meal: Meal?) {
                if (meal != null) {
                    Glide.with(this@HomeFragment).load(meal.strMealThumb)
                        .into(binding?.ivRandomMeal!!)
                }
            }

        })
    }

}