package com.example.meals.activites

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.meals.R
import com.example.meals.db.MealDatabase
import com.example.meals.pojo.Meal
import com.example.meals.retrofit.MealApi
import com.example.meals.retrofit.RetrofitInstance
import com.example.meals.viewmodel.HomeViewModel
import com.example.meals.viewmodel.HomeViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    val viewModel:HomeViewModel by lazy {
        val mealDao = MealDatabase.getDatabase(this).mealDao()
        val mealApi = RetrofitInstance.getInstance().create(MealApi::class.java)
        ViewModelProvider(this,HomeViewModelFactory(mealApi,mealDao))[HomeViewModel::class.java ]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_main)



        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_nav)
        val navController = Navigation.findNavController(this, R.id.home_fragment_holder)
        NavigationUI.setupWithNavController(bottomNavigation,navController)

    }
}