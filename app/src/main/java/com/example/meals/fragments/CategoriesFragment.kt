package com.example.meals.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.meals.R
import com.example.meals.activites.CategoryMealsActivity
import com.example.meals.activites.MainActivity
import com.example.meals.adapters.MealsCategoryAdapter
import com.example.meals.databinding.FragmentCategoriesBinding
import com.example.meals.network.BaseFragment
import com.example.meals.pojo.Category
import com.example.meals.viewmodel.HomeViewModel

class CategoriesFragment : BaseFragment() {

    private lateinit var binding:FragmentCategoriesBinding
    private lateinit var mealsCategoryAdapter: MealsCategoryAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mealsCategoryAdapter = MealsCategoryAdapter()
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onNetworkAvailable() {
        super.onNetworkAvailable()
        prepareRecyclerView()
        viewModel.getAllMealCategories()
        settingData()
        handleItemClicks()
    }

    private fun handleItemClicks() {
        mealsCategoryAdapter.onItemClick = {
            val intent = Intent(requireActivity(),CategoryMealsActivity::class.java)
            intent.putExtra(HomeFragment.mealCategory,it.strCategory)
            startActivity(intent)
        }
    }

    private fun settingData() {
        viewModel.mealCategories.observe(viewLifecycleOwner, Observer {
            mealsCategoryAdapter.setData(it as ArrayList<Category>)
        })
    }

    private fun prepareRecyclerView() {
        binding.mealCategoryRv.apply {
            adapter = mealsCategoryAdapter
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
        }
    }
}