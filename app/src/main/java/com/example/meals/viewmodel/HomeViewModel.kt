package com.example.meals.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meals.db.MealDao
import com.example.meals.network.NetworkConnectivityCheck
import com.example.meals.pojo.Category
import com.example.meals.pojo.MealsByCategory
import com.example.meals.pojo.Meal
import com.example.meals.retrofit.MealApi
import com.example.meals.utils.InternetConnectivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val retrofitInstance: MealApi,private val mealDao: MealDao) : ViewModel() {

    private var meals = MutableLiveData<Meal>()
    val mealsData: LiveData<Meal> = meals

    fun getRandomMeals() {

        viewModelScope.launch(Dispatchers.IO) {
            val result = retrofitInstance.getSingleRandomMeal()
            if (result.body()!=null) {
                meals.postValue(result.body()!!.meals[0])
            }
            else{
                Log.d("HomeFragment","Error fetching random meal ${result.errorBody()}")
            }
        }

    }

    private var popularMeal = MutableLiveData<List<MealsByCategory>>()
    val popularMealData:LiveData<List<MealsByCategory>> = popularMeal

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

    private var mealCategoriesData = MutableLiveData<List<Category>>()
    val mealCategories:LiveData<List<Category>> = mealCategoriesData

    fun getAllMealCategories(){

        viewModelScope.launch(Dispatchers.IO) {
            val result = retrofitInstance.getMealCategories()
            if(result.body()!=null){
                mealCategoriesData.postValue(result.body()!!.categories)
            }else{
                Log.d("HomeFragment","Error fetching meal categories ${result.errorBody()}")
            }
        }
    }




    fun getAllFavouriteMeal():LiveData<List<Meal>>{

        return  mealDao.getAllMeals()
    }

    private var searchedMealListData = MutableLiveData<List<Meal>>()
    val searchedMealList:LiveData<List<Meal>> = searchedMealListData
    fun getMealByName(name:String){

        viewModelScope.launch(Dispatchers.IO) {
           val result = retrofitInstance.searchMeal(name)
            if(result.body()!=null){
                searchedMealListData.postValue(result.body()!!.meals)
            }else{
                Log.d("Search Fragment","Error fetching data for search fragment ${result.errorBody()}")
            }
        }
    }

    fun deleteFavouriteMeal(meal: Meal){
        viewModelScope.launch(Dispatchers.IO) {
            mealDao.delete(meal)
        }
    }

    fun addFavouriteMeal(meal:Meal){

        viewModelScope.launch(Dispatchers.IO) {
            mealDao.insert(meal)
        }
    }

}