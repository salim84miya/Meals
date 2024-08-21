package com.example.meals.activites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.meals.R
import com.example.meals.adapters.MealsByCategoryAdapter
import com.example.meals.adapters.MealsCategoryAdapter
import com.example.meals.databinding.ActivityCategoryMealsBinding
import com.example.meals.fragments.HomeFragment
import com.example.meals.network.BaseActivity
import com.example.meals.pojo.Category
import com.example.meals.pojo.Meal
import com.example.meals.pojo.MealsByCategory
import com.example.meals.retrofit.MealApi
import com.example.meals.retrofit.RetrofitInstance
import com.example.meals.viewmodel.MealsByCategoryViewModel
import com.example.meals.viewmodel.MealsByCategoryViewModelFactory

class CategoryMealsActivity : BaseActivity() {

    private lateinit var binding: ActivityCategoryMealsBinding
    private lateinit var mealsByCategoryAdapter: MealsByCategoryAdapter
    private lateinit var mealsByCategoryViewModel: MealsByCategoryViewModel
    private lateinit var category:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onNetworkAvailable() {
        super.onNetworkAvailable()

        mealsByCategoryAdapter = MealsByCategoryAdapter()

        val retrofit = RetrofitInstance.getInstance().create(MealApi::class.java)

        mealsByCategoryViewModel = ViewModelProvider(this,MealsByCategoryViewModelFactory(retrofit))[MealsByCategoryViewModel::class.java]

        category =intent.getStringExtra(HomeFragment.mealCategory)!!

        settingRecyclerview()
        mealsByCategoryViewModel.getMealsByCategory(category)
        gettingData()
        settingMealsCount()
        handlingClicks()
    }

    private fun handlingClicks() {
        mealsByCategoryAdapter.onItemClick = {
            val intent = Intent(this,MealActivity::class.java)
            intent.putExtra(HomeFragment.mealId,it.idMeal)
            intent.putExtra(HomeFragment.mealName,it.strMeal)
            intent.putExtra(HomeFragment.mealThumb,it.strMealThumb)
            startActivity(intent)
        }


    }

    private fun settingMealsCount() {
        mealsByCategoryViewModel.mealsByCategoryCount.observe(this, Observer {
            binding.tvCategoryMealCount.text = "${category} :${it}"
        })
    }

    private fun gettingData() {
        mealsByCategoryViewModel.mealsByCategory.observe(this, Observer {

            mealsByCategoryAdapter.setData(it as ArrayList<MealsByCategory>)
        })
    }

    private fun settingRecyclerview() {
        binding.categoryMealsRv.apply {
            adapter = mealsByCategoryAdapter
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
        }
    }
}