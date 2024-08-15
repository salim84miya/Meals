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
import com.bumptech.glide.Glide
import com.example.meals.R
import com.example.meals.activites.MealActivity
import com.example.meals.databinding.FragmentHomeBinding
import com.example.meals.pojo.Meal
import com.example.meals.pojo.RandomMealList
import com.example.meals.retrofit.MealApi
import com.example.meals.retrofit.RetrofitInstance
import com.example.meals.viewmodel.HomeViewModel
import com.example.meals.viewmodel.HomeViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class HomeFragment : Fragment() {
        private lateinit var binding: FragmentHomeBinding
        private lateinit var viewmodel:HomeViewModel
        private lateinit var randomMeal: Meal

        companion object{
            val mealId:String = "com.example.meals.fragments.mealId"
            val mealName:String = "com.example.meals.fragments.mealName"
            val mealThumb:String = "com.example.meals.fragments.mealThumb"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val retrofitInstance =RetrofitInstance.getInstance().create(MealApi::class.java)

        viewmodel = ViewModelProvider(this@HomeFragment,HomeViewModelFactory(retrofitInstance))[HomeViewModel::class.java]
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

        viewmodel.getRandomMeals()
        observeRandomMealData()
        startRandomMealDetailActivity()

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
        viewmodel.mealsData.observe(viewLifecycleOwner, Observer {
            Glide.with(requireActivity()).load(it.strMealThumb).into(binding.imgRandomMeal)
            this.randomMeal = it
        })
    }
}