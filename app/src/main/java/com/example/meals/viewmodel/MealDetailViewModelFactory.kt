package com.example.meals.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.meals.retrofit.MealApi

class MealDetailViewModelFactory(private val retrofitApi:MealApi):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealDetailViewModel(retrofitApi) as T
    }
}