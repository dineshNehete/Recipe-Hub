package com.example.recipeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.databinding.FragmentFavoriteBinding
import com.example.recipeapp.databinding.MealItemBinding
import com.example.recipeapp.pojo.Meal

class FavoriteMealItemAdapter :
    RecyclerView.Adapter<FavoriteMealItemAdapter.FavoriteMealsViewHolder>() {

    // diffUtil is an efficient way to manage recycler views, by using this, if out of 100 items any of the items are changes instead of changing or loading the whole recycler view again, we load only those items which are changed.
    private val diffUtil = object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }

    }

    // second step of setting up the diffUtil, we will use this to retrieve the item
    val differ = AsyncListDiffer(this, diffUtil)


    inner class FavoriteMealsViewHolder(val binding: MealItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMealsViewHolder {
        return FavoriteMealsViewHolder(MealItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)
        )
    }

    override fun onBindViewHolder(holder: FavoriteMealsViewHolder, position: Int) {
        val meal = differ.currentList[position] // this will return the meal list
        Glide.with(holder.itemView).load(meal.strMealThumb).into(holder.binding.ivMeal)
        holder.binding.tvMealName.text = meal.strMeal
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}