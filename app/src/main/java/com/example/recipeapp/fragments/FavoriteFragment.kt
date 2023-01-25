package com.example.recipeapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.activities.MainActivity
import com.example.recipeapp.adapters.FavoriteMealItemAdapter
import com.example.recipeapp.databinding.FragmentFavoriteBinding
import com.example.recipeapp.pojo.Meal
import com.example.recipeapp.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class FavoriteFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favoriteMealItemAdapter: FavoriteMealItemAdapter
    private lateinit var present: LiveData<List<Meal>>


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


        // this class manages the touch, this will allow to add functionality when we swipe a particular item in ay specific direction
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deletedMeal = favoriteMealItemAdapter.differ.currentList[position]
                viewModel.deleteMealFromFavorites(deletedMeal)
                Snackbar.make(requireView(), "Meal Deleted", Snackbar.LENGTH_LONG).setAction(
                    "Undo",
                    View.OnClickListener {
                        viewModel.insertIntoFavorites(deletedMeal)
                    }).show()
            }

        }
        // attaching the above implemented function to the recycler view
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavoriteMeals)
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