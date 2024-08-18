package com.example.meals.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meals.databinding.CategoryItemBinding
import com.example.meals.pojo.Category

class MealsCategoryAdapter:RecyclerView.Adapter<MealsCategoryAdapter.RowViewHolder>() {

    private var categoryList:List<Category> = ArrayList()
    var onItemClick:((Category)->Unit)? = null

    fun setData(categoryList:ArrayList<Category>){
        this.categoryList = categoryList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowViewHolder {
        return RowViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
       return categoryList.size
    }

    override fun onBindViewHolder(holder: RowViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoryList[position].strCategoryThumb)
            .into(holder.binding.imgCategory)

        holder.binding.tvCategoryName.text = categoryList[position].strCategory

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(categoryList[position])
        }
    }

    class RowViewHolder(val binding:CategoryItemBinding):RecyclerView.ViewHolder(binding.root){

    }
}