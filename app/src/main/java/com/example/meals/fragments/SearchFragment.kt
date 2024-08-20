package com.example.meals.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.GridLayoutManager
import com.example.meals.R
import com.example.meals.activites.MainActivity
import com.example.meals.activites.MealActivity
import com.example.meals.adapters.FavouriteItemAdapter
import com.example.meals.databinding.FragmentSearchBinding
import com.example.meals.viewmodel.HomeViewModel


class SearchFragment : Fragment() {

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

        prepareRecyclerView()
        settingDataForRv()
        searchMeal()
        handlingClicks()
    }

    private fun searchMeal() {
        binding.imgSearchBtn.setOnClickListener {
            val searchQuery = binding.searchBox.text.toString()
            if(searchQuery.isNotEmpty()){
                viewModel.getMealByName(searchQuery.trim())
            }
        }
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