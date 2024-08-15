package com.example.meals.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.meals.retrofit.MealApi

class HomeViewModelFactory(private val retrofit:MealApi):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(retrofit) as T
    }
}