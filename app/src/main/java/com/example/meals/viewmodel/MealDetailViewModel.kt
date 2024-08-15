package com.example.meals.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meals.pojo.Meal
import com.example.meals.retrofit.MealApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MealDetailViewModel(private val retrofitApi:MealApi):ViewModel() {

    private var mealData = MutableLiveData<Meal>()
    val meal:LiveData<Meal> = mealData


    fun getMealDetails(id:String){
        viewModelScope.launch(Dispatchers.IO) {
            val result = retrofitApi.getRandomMealDetail(id)
            if(result.body()!=null){
                mealData.postValue(result.body()!!.meals[0])
            }else{
                Log.d("MealDetailViewModel",result.message())
            }
        }
    }
}