package com.example.meals.fragments

import android.content.Context
import android.content.Intent
import android.hardware.input.InputManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.meals.activites.MainActivity
import com.example.meals.activites.MealActivity
import com.example.meals.adapters.FavouriteItemAdapter
import com.example.meals.databinding.FragmentSearchBinding
import com.example.meals.network.BaseFragment
import com.example.meals.viewmodel.HomeViewModel


class SearchFragment : BaseFragment() {

    private lateinit var binding:FragmentSearchBinding
    private lateinit var searchItemAdapter:FavouriteItemAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        searchItemAdapter = FavouriteItemAdapter()
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onNetworkAvailable() {
        super.onNetworkAvailable()
        prepareRecyclerView()
        settingDataForRv()
        searchMeal()
        handlingClicks()
    }



    private fun searchMeal() {
        binding.imgSearchBtn.setOnClickListener {
            hideKeyboard(it)
            val searchQuery = binding.searchBox.text.toString()
            if(searchQuery.isNotEmpty()){
                viewModel.getMealByName(searchQuery.trim())
            }
        }
    }

    private fun hideKeyboard(view: View) {
        val imm = context?.applicationContext?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun handlingClicks() {
        searchItemAdapter.onItemClick = {
            val intent = Intent(requireActivity(),MealActivity::class.java)
            intent.apply {
                putExtra(HomeFragment.mealId,it.idMeal)
                putExtra(HomeFragment.mealName,it.strMeal)
                putExtra(HomeFragment.mealThumb,it.strMealThumb)
                startActivity(this)
            }
        }
    }

    private fun settingDataForRv() {
        viewModel.searchedMealList.observe(viewLifecycleOwner, Observer {
            searchItemAdapter.differ.submitList(it)
        })
    }

    private fun prepareRecyclerView() {
       binding.searchItemRv.apply {
           adapter = searchItemAdapter
           layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
       }
    }
}