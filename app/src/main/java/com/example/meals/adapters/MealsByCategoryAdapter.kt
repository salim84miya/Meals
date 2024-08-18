package com.example.meals.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meals.databinding.CategoryMealsBinding
import com.example.meals.pojo.MealsByCategory

class MealsByCategoryAdapter:RecyclerView.Adapter<MealsByCategoryAdapter.RowHolder>() {

    private var mealsByCategoryList:List<MealsByCategory> = ArrayList()
    var onItemClick:((MealsByCategory)->Unit)? = null

    fun setData(mealsByCategoryList:ArrayList<MealsByCategory>){
        this.mealsByCategoryList = mealsByCategoryList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
    return RowHolder(CategoryMealsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return mealsByCategoryList.size
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsByCategoryList[position].strMealThumb)
            .into(holder.binding.imgCategoryMeal)

        holder.binding.tvCategoryMealName.text = mealsByCategoryList[position].strMeal

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(mealsByCategoryList[position])
        }

    }

    class RowHolder(val binding:CategoryMealsBinding):RecyclerView.ViewHolder(binding.root){
    }
}