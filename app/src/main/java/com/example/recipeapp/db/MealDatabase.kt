package com.example.recipeapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recipeapp.pojo.Meal


@Database(entities = [Meal::class], version = 1)
@TypeConverters(MealTypeConvertor::class)
abstract class MealDatabase : RoomDatabase() {
    //Creating an object using a function, such as the example provided in the abstract statement "fun mealDao() : MealDao," is a common technique in software development known as a factory method. This approach allows for the encapsulation of object creation logic in a single location, making it more organized and easier to modify or update in the future. Additionally, it allows for greater flexibility in the types of objects that can be created and returned, as the factory method can implement various conditions or logic to determine which specific implementation of the desired object should be instantiated and returned.
    abstract fun mealDao(): MealDao

    companion object {
        @Volatile
        private var INSTANCE: MealDatabase? = null

        @Synchronized
        fun getInstance(context: Context): MealDatabase {
            var instance = INSTANCE
            if (instance == null) {
                // fallbacktodestructmigration means -> in case of failure the database should be recreated with retaining all of the data alreayd present in the database
                instance = Room.databaseBuilder(context, MealDatabase::class.java, "meal.db")
                    .fallbackToDestructiveMigration().build()
                INSTANCE = instance
            }
            return INSTANCE as MealDatabase
        }
    }

}