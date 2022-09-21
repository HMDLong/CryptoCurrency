package com.hmdlong14.cryptocurrency.screens.coin.coinlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hmdlong14.cryptocurrency.R
import com.hmdlong14.cryptocurrency.databinding.CategoryItemBinding


interface CategoryClickListener {
    fun onCategoryClick(categoryPos : Int)
}

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {
    lateinit var listener: CategoryClickListener
    private var currentCategoryPos = RANKING_POS

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.categoryTitle.text = categories[position]
        holder.categoryTitle.setTextColor(holder.itemView.resources.getColor(
            if(position == currentCategoryPos)
                R.color.color_dark_blue
            else
                R.color.color_black
        ))
        holder.itemView.setOnClickListener {
            currentCategoryPos = holder.adapterPosition
            listener.onCategoryClick(holder.adapterPosition)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = categories.size

    inner class CategoryHolder(itemView: View, binding: CategoryItemBinding) : RecyclerView.ViewHolder(itemView) {
        val categoryTitle = binding.categoryTitle
    }

    companion object {
        val categories = listOf(
            "Favorite",
            "Ranking",
            "Market Cap",
            "24h Volume",
            "Price",
        )
        const val FAV_POS = 0
        const val RANKING_POS = 1
        const val MARKET_CAP_POS = 2
        const val VOLUME_24H_POS = 3
        const val PRICE_POS = 4
    }
}
