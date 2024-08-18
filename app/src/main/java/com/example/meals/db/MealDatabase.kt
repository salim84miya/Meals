package com.example.meals.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.meals.pojo.Meal

@Database(entities = [Meal::class], version = 1)
@TypeConverters(DateTypeConvertor::class)
abstract class MealDatabase:RoomDatabase() {

    abstract fun mealDao():MealDao

    companion object{
        @Volatile
        var instance:MealDatabase?= null

        fun getDatabase(context:Context):MealDatabase{
            if(instance == null){
                synchronized(this){
                    instance = Room.databaseBuilder(context,MealDatabase::class.java,"mealDb").build()
                }
            }
            return instance!!
        }
    }
}