package com.example.meals.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meals.pojo.MealsByCategory
import com.example.meals.retrofit.MealApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MealsByCategoryViewModel(private val retrofitInstance:MealApi):ViewModel() {


    private var mealsByCategoryData = MutableLiveData<List<MealsByCategory>>()
    val mealsByCategory:LiveData<List<MealsByCategory>> = mealsByCategoryData

    private var mealsByCategoryCountData  = MutableLiveData<Int>()
    val mealsByCategoryCount:LiveData<Int> = mealsByCategoryCountData

    fun getMealsByCategory(category:String){

        viewModelScope.launch(Dispatchers.IO) {
            val result = retrofitInstance.getMealsByCategory(category)
            if(result.body()!=null){
                mealsByCategoryData.postValue(result.body()!!.meals)
                mealsByCategoryCountData.postValue(result.body()!!.meals.size)
            }else{
                Log.d("Category Meals Activity ","Error Fetching data ${result.errorBody()}")
            }
        }
    }
}