package com.example.meals.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.meals.R
import com.example.meals.activites.MainActivity
import com.example.meals.adapters.FavouriteItemAdapter
import com.example.meals.databinding.FragmentFavouriteBinding
import com.example.meals.viewmodel.HomeViewModel


class FavouriteFragment : Fragment() {

    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var favouriteItemAdapter: FavouriteItemAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouriteBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllFavouriteMeal()
        prepareRecyclerView()
        settingDataToAdapter()
    }

    private fun settingDataToAdapter() {
        viewModel.getAllFavouriteMeal().observe(viewLifecycleOwner, Observer {
            favouriteItemAdapter.differ.submitList(it)
            it.forEach {
                Log.d("Favourite Fragment",it.strMeal)
            }

        })
    }

    private fun prepareRecyclerView() {
        favouriteItemAdapter = FavouriteItemAdapter()
        binding.favouriteItemsRv.apply {
            adapter = favouriteItemAdapter
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
        }
    }
}