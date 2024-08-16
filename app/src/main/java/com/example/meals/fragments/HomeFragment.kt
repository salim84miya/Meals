package com.example.meals.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.meals.activites.MealActivity
import com.example.meals.adapters.OverPopularItemAdapter
import com.example.meals.databinding.FragmentHomeBinding
import com.example.meals.pojo.Categories
import com.example.meals.pojo.Meal
import com.example.meals.retrofit.MealApi
import com.example.meals.retrofit.RetrofitInstance
import com.example.meals.viewmodel.HomeViewModel
import com.example.meals.viewmodel.HomeViewModelFactory

class HomeFragment : Fragment() {
        private lateinit var binding: FragmentHomeBinding
        private lateinit var homeViewModel:HomeViewModel
        private lateinit var randomMeal: Meal
        private lateinit var overPopularItemAdapter: OverPopularItemAdapter

        companion object{
            val mealId:String = "com.example.meals.fragments.mealId"
            val mealName:String = "com.example.meals.fragments.mealName"
            val mealThumb:String = "com.example.meals.fragments.mealThumb"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val retrofitInstance =RetrofitInstance.getInstance().create(MealApi::class.java)

        homeViewModel = ViewModelProvider(this@HomeFragment,HomeViewModelFactory(retrofitInstance))[HomeViewModel::class.java]

        overPopularItemAdapter = OverPopularItemAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingRecyclerViewForPopularItem()

        homeViewModel.getRandomMeals()
        observeRandomMealData()
        startRandomMealDetailActivity()

        homeViewModel.getPopularMeals("Seafood")
        gettingDataForPopularItem()

        settingClickOnPopularMeals()



    }

    private fun settingClickOnPopularMeals() {
        overPopularItemAdapter.onItemOnclick ={meal->
            val intent = Intent(requireActivity(),MealActivity::class.java)
            intent.putExtra(mealId,meal.idMeal)
            intent.putExtra(mealName,meal.strMeal)
            intent.putExtra(mealThumb,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun gettingDataForPopularItem() {
        homeViewModel.popularMealData.observe(viewLifecycleOwner, Observer {
            overPopularItemAdapter.setData(popularItemList = it as ArrayList<Categories>)
        })
    }

    private fun settingRecyclerViewForPopularItem() {
        binding.overPopularItemRv.apply {
            layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)
            adapter = overPopularItemAdapter
        }
    }

    private fun startRandomMealDetailActivity() {
        binding.imgRandomMeal.setOnClickListener {
            val intent = Intent(requireActivity(),MealActivity::class.java)
            intent.putExtra(mealId,randomMeal.idMeal)
            intent.putExtra(mealName,randomMeal.strMeal)
            intent.putExtra(mealThumb,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeRandomMealData(){
        homeViewModel.mealsData.observe(viewLifecycleOwner, Observer {
            Glide.with(requireActivity()).load(it.strMealThumb).into(binding.imgRandomMeal)
            this.randomMeal = it
        })
    }
}