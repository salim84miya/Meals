package com.example.meals.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meals.databinding.CategoryMealsBinding
import com.example.meals.pojo.Meal
import java.util.Objects

class FavouriteItemAdapter:RecyclerView.Adapter<FavouriteItemAdapter.RowHolder>() {

    lateinit var onItemClick:((Meal)->Unit)
    inner class RowHolder(val binding:CategoryMealsBinding):RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
           return oldItem==newItem
        }

    }

    val differ = AsyncListDiffer(this,diffUtil)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        return RowHolder(CategoryMealsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        val meal = differ.currentList[position]

        Glide.with(holder.itemView).load(meal.strMealThumb).into(holder.binding.imgCategoryMeal)
        holder.binding.tvCategoryMealName.text = meal.strMeal

        holder.itemView.setOnClickListener {
            onItemClick.invoke(meal)
        }
    }
}