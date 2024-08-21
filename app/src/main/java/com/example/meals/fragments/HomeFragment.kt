package com.example.meals.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.meals.R
import com.example.meals.activites.CategoryMealsActivity
import com.example.meals.activites.MainActivity
import com.example.meals.activites.MealActivity
import com.example.meals.adapters.MealsCategoryAdapter
import com.example.meals.adapters.OverPopularItemAdapter
import com.example.meals.databinding.FragmentHomeBinding
import com.example.meals.network.BaseFragment
import com.example.meals.network.NetworkConnectivityCheck
import com.example.meals.pojo.Category
import com.example.meals.pojo.MealsByCategory
import com.example.meals.pojo.Meal
import com.example.meals.retrofit.MealApi
import com.example.meals.retrofit.RetrofitInstance
import com.example.meals.utils.InternetConnectivity
import com.example.meals.viewmodel.HomeViewModel
import com.example.meals.viewmodel.HomeViewModelFactory

class HomeFragment : BaseFragment() {
        private lateinit var binding: FragmentHomeBinding
        private lateinit var homeViewModel:HomeViewModel
        private lateinit var Meal: Meal
        private lateinit var overPopularItemAdapter: OverPopularItemAdapter
        private lateinit var mealsCategoryAdapter:MealsCategoryAdapter

        companion object{
            val mealId:String = "com.example.Meals.fragments.mealId"
            val mealName:String = "com.example.Meals.fragments.mealName"
            val mealThumb:String = "com.example.Meals.fragments.mealThumb"
            val mealCategory:String = "com.example.Meals.fragments.mealCategory"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeViewModel = (activity as MainActivity).viewModel

        overPopularItemAdapter = OverPopularItemAdapter()
        mealsCategoryAdapter = MealsCategoryAdapter()

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
    }

    override fun onNetworkAvailable() {
        super.onNetworkAvailable()

        settingRecyclerViewForPopularItem()

        homeViewModel.getRandomMeals()
        observeRandomMealData()
        startRandomMealDetailActivity()

        homeViewModel.getPopularMeals("Seafood")
        gettingDataForPopularItem()
        settingClickOnPopularMeals()

        homeViewModel.getAllMealCategories()
        gettingDataForAllMealCategories()
        settingRecyclerViewForAllCategories()
        settingClickOnCategories()

        onSeachIconClick()
    }

    private fun onSeachIconClick() {
        binding.headerSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun settingClickOnCategories() {
        mealsCategoryAdapter.onItemClick = {
            val intent = Intent(requireActivity(),CategoryMealsActivity::class.java)
            intent.putExtra(mealCategory,it.strCategory)
            startActivity(intent)
        }

    }

    private fun settingRecyclerViewForAllCategories() {
        binding.recyclerView.apply {
            adapter = mealsCategoryAdapter
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.HORIZONTAL,false)
        }
    }

    private fun gettingDataForAllMealCategories() {
        homeViewModel.mealCategories.observe(viewLifecycleOwner, Observer {
            mealsCategoryAdapter.setData(it as ArrayList<Category>)
        })
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
            overPopularItemAdapter.setData(popularItemList = it as ArrayList<MealsByCategory>)
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
            intent.putExtra(mealId,Meal.idMeal)
            intent.putExtra(mealName,Meal.strMeal)
            intent.putExtra(mealThumb,Meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeRandomMealData(){
        homeViewModel.mealsData.observe(viewLifecycleOwner, Observer {
            Glide.with(requireActivity()).load(it.strMealThumb).into(binding.imgRandomMeal)
            this.Meal = it
        })
    }
}