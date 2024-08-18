package com.example.meals.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.meals.retrofit.MealApi

class MealsByCategoryViewModelFactory(private val retrofitInstance:MealApi):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealsByCategoryViewModel(retrofitInstance) as T
    }
}