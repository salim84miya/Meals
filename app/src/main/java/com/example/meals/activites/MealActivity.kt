package com.example.meals.activites

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.meals.R
import com.example.meals.databinding.ActivityMealBinding
import com.example.meals.db.MealDao
import com.example.meals.db.MealDatabase
import com.example.meals.fragments.HomeFragment
import com.example.meals.network.BaseActivity
import com.example.meals.pojo.Meal
import com.example.meals.retrofit.MealApi
import com.example.meals.retrofit.RetrofitInstance
import com.example.meals.viewmodel.MealDetailViewModel
import com.example.meals.viewmodel.MealDetailViewModelFactory
import com.google.android.material.snackbar.Snackbar


class MealActivity : BaseActivity(){

    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var mealYtLink:String
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealDetailViewModel: MealDetailViewModel
    private lateinit var database: MealDao
    private lateinit var meal:Meal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()

        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onNetworkAvailable() {
        super.onNetworkAvailable()
        database = MealDatabase.getDatabase(this).mealDao()
        val retrofitApi = RetrofitInstance.getInstance().create(MealApi::class.java)
        mealDetailViewModel = ViewModelProvider(this, MealDetailViewModelFactory(retrofitApi,database))[MealDetailViewModel::class.java]

        getDataFromIntent()
        setDataOfIntent()

        loadingCase()
        mealDetailViewModel.getMealDetails(mealId)
        setMealDetails()
    }

    private fun favouriteItemClick() {
        binding.favouriteBtn.setOnClickListener {
            mealDetailViewModel.addFavouriteMeal(meal)

            Snackbar.make(findViewById(R.id.main),"Meal added to favourite",Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setYoutubeClicks() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW,Uri.parse(mealYtLink))
            startActivity(intent)
        }
    }

    private fun setMealDetails() {
        mealDetailViewModel.meal.observe(this, Observer {

            dataReadyCase()

            meal = it

            binding.tvMealInstructions.text = it.strInstructions
            binding.mealCategory.text = "Category :${it.strCategory}"
            binding.mealCousine.text = "Cousine :${it.strArea}"

            mealYtLink = it.strYoutube

            setYoutubeClicks()

            favouriteItemClick()
        })

    }

    private fun setDataOfIntent() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.detailPageMealImg)

        binding.collapsableToolbar.title = mealName
        binding.collapsableToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsableToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getDataFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.mealId).toString()
        mealName = intent.getStringExtra(HomeFragment.mealName).toString()
        mealThumb = intent.getStringExtra(HomeFragment.mealThumb).toString()
    }

    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.mealCategory.visibility = View.INVISIBLE
        binding.mealCousine.visibility = View.INVISIBLE
        binding.tvInstruction.visibility = View.INVISIBLE
        binding.tvMealInstructions.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }

    private fun dataReadyCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.mealCategory.visibility = View.VISIBLE
        binding.mealCousine.visibility = View.VISIBLE
        binding.tvInstruction.visibility = View.VISIBLE
        binding.tvMealInstructions.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }
}