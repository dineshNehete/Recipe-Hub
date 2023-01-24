package com.example.recipeapp.adapters

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.databinding.ActivityCategoryMealsBinding
import com.example.recipeapp.databinding.MealItemBinding
import com.example.recipeapp.pojo.PopularMealsByCategory

class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewHolder>() {
    var mealsList = ArrayList<PopularMealsByCategory>()

    fun setMealsList(mealsList: List<PopularMealsByCategory>) {
        this.mealsList = mealsList as ArrayList<PopularMealsByCategory>
        notifyDataSetChanged()
    }


    inner class CategoryMealsViewHolder(
        val mealItemBinding: MealItemBinding,
    ) : RecyclerView.ViewHolder(
        mealItemBinding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
        return CategoryMealsViewHolder(MealItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {
        Glide.with(holder.itemView).load(mealsList[position].strMealThumb).into(holder.mealItemBinding.ivMeal)
        holder.mealItemBinding.tvMealName.text = mealsList[position].strMeal
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }
}