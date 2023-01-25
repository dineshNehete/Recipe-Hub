package com.example.recipeapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.recipeapp.pojo.Meal

// Dao should contain the functions you want to carry out on the respective table, as this is MealDao this interface should contain the function definitions of the operations to be performed on the meal table in the roomdb
@Dao
interface MealDao {
    @Insert
    suspend fun insertMeal(meal : Meal)

    @Update
    suspend fun updateMeal(meal: Meal)

    @Delete
    suspend fun deleteMeal(meal: Meal)

    @Query("SELECT * FROM mealInformation")
    fun getAllMeal(): LiveData<List<Meal>>

    @Query("SELECT * FROM mealInformation WHERE idMeal = :id")
    fun isRecordPresent(id: String): LiveData<List<Meal>>
}