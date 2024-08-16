package com.example.meals.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meals.databinding.OverPopularItemViewBinding
import com.example.meals.pojo.Categories

class OverPopularItemAdapter: RecyclerView.Adapter<OverPopularItemAdapter.OverPopularItemHolder>() {

    lateinit var onItemOnclick:((Categories)->Unit)
    private var popularItemList = ArrayList<Categories>()

    fun setData(popularItemList:ArrayList<Categories>){
            this.popularItemList = popularItemList
            notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OverPopularItemHolder {
        return OverPopularItemHolder(OverPopularItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    override fun getItemCount(): Int {
        return popularItemList.size
    }

    override fun onBindViewHolder(holder: OverPopularItemHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(popularItemList[position].strMealThumb)
            .into(holder.binding.imgPopularItem)

        holder.itemView.setOnClickListener {
            onItemOnclick.invoke(popularItemList[position])
        }
    }

    class OverPopularItemHolder(val binding: OverPopularItemViewBinding):RecyclerView.ViewHolder(binding.root){

    }
}