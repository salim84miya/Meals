package com.example.meals.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meals.pojo.Categories
import com.example.meals.pojo.Meal
import com.example.meals.retrofit.MealApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val retrofitInstance: MealApi) : ViewModel() {

    private var meals = MutableLiveData<Meal>()
    val mealsData: LiveData<Meal> = meals

    fun getRandomMeals() {

        viewModelScope.launch(Dispatchers.IO) {
            val result = retrofitInstance.getRandomMeal()
            if (result.body() != null) {
                meals.postValue(result.body()!!.meals[0])
            }
            else{
                Log.d("HomeFragment",result.message())
            }
        }

    }

    private var popularMeal = MutableLiveData<List<Categories>>()
    val popularMealData:LiveData<List<Categories>> = popularMeal

    fun getPopularMeals(categories: String){

        viewModelScope.launch(Dispatchers.IO) {
            val result = retrofitInstance.getMealsByCategory(categories)
            if(result.body()!=null){
                popularMeal.postValue(result.body()!!.meals)
            }else{
                Log.d("Popular Meal Response result",result.message())
            }
        }
    }
}