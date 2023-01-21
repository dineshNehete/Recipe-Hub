package com.example.recipeapp.adapters

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.databinding.PopularFoodItemBinding
import com.example.recipeapp.pojo.CategoryMeals

class PopularItemsAdapter() : RecyclerView.Adapter<PopularItemsAdapter.PopularItemViewHolder>() {
    private var mealsList = ArrayList<CategoryMeals>()
    // to handle the item clicks
    lateinit var onItemClick: ((CategoryMeals) -> Unit)

    fun setMeals(mealsList: ArrayList<CategoryMeals>) {
        this.mealsList = mealsList
        notifyDataSetChanged()
    }

    class PopularItemViewHolder(var binding: PopularFoodItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularItemViewHolder {
        return PopularItemViewHolder(
            PopularFoodItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(holder: PopularItemViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(this.mealsList.get(position).strMealThumb)
            .into(holder.binding.ivPopularMealItem)

        // one of the method to add the itemclicks, if commented out then the click function is not recognized
        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealsList[position])
        }
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }
}