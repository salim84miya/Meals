package com.example.meals.fragments

import android.content.Intent
import android.media.browse.MediaBrowser.ItemCallback
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.meals.R
import com.example.meals.activites.MainActivity
import com.example.meals.activites.MealActivity
import com.example.meals.adapters.FavouriteItemAdapter
import com.example.meals.databinding.FragmentFavouriteBinding
import com.example.meals.network.BaseFragment
import com.example.meals.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar


class FavouriteFragment : BaseFragment() {

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
    }

    override fun onNetworkAvailable() {
        super.onNetworkAvailable()
        viewModel.getAllFavouriteMeal()
        prepareRecyclerView()
        settingDataToAdapter()
        handleOnItemClick()
        deleteItems()
    }

    private fun deleteItems() {
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
               return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
               val position = viewHolder.adapterPosition
                var deleteMeal = favouriteItemAdapter.differ.currentList[position]

                favouriteItemAdapter.notifyItemRemoved(position)
                viewModel.deleteFavouriteMeal(deleteMeal)

                Snackbar.make(requireView(),"Meal deleted",Snackbar.LENGTH_SHORT).setAction(
                    "Undo", View.OnClickListener {
                        viewModel.addFavouriteMeal(deleteMeal)
                        favouriteItemAdapter.notifyDataSetChanged()
                    }
                ).show()
            }

        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.favouriteItemsRv)
    }

    private fun handleOnItemClick() {
        favouriteItemAdapter.onItemClick = {
            val intent = Intent(requireActivity(),MealActivity::class.java)
            intent.putExtra(HomeFragment.mealId,it.idMeal)
            intent.putExtra(HomeFragment.mealName,it.strMeal)
            intent.putExtra(HomeFragment.mealThumb,it.strMealThumb)
            startActivity(intent)
        }
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