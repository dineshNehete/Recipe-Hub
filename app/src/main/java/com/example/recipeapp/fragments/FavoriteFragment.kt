package com.example.recipeapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.recipeapp.R
import com.example.recipeapp.activities.MainActivity
import com.example.recipeapp.adapters.FavoriteMealItemAdapter
import com.example.recipeapp.databinding.FragmentFavoriteBinding
import com.example.recipeapp.viewmodel.HomeViewModel

class FavoriteFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favoriteMealItemAdapter: FavoriteMealItemAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeFavorites()

    }

    private fun prepareRecyclerView() {
        favoriteMealItemAdapter = FavoriteMealItemAdapter()
        binding.rvFavoriteMeals.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = favoriteMealItemAdapter
        }
    }

    private fun observeFavorites() {
        viewModel.observerFavoritesMealsLiveData().observe(requireActivity(), Observer { meals ->
            meals.forEach {
                Log.d("test", it.idMeal)
            }
            favoriteMealItemAdapter.differ.submitList(meals)
        })
    }

}