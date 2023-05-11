package com.example.b01_g01_mobile_masters.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.b01_g01_mobile_masters.R
import com.example.b01_g01_mobile_masters.databinding.LayoutCategoryItemBinding
import com.example.b01_g01_mobile_masters.model.CategoryModel

class CategoryAdapter(var context: Context, private val list : ArrayList<CategoryModel>)
    : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){

    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var binding = LayoutCategoryItemBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_category_item, parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.binding.textView.text = list[position].cat
        Glide.with(context).load(list[position].img).into(holder.binding.imageView)
    }
}
