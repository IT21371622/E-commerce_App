package com.example.b01_g01_mobile_masters.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.b01_g01_mobile_masters.databinding.LayoutProductItemBinding
import com.example.b01_g01_mobile_masters.model.AddProductModel

class ProductAdapter(val context: Context, val list: ArrayList<AddProductModel>)
    :RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){

    inner class ProductViewHolder(val binding: LayoutProductItemBinding)
        :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = LayoutProductItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val data = list[position]

        Glide.with(context).load(data.productCoverImg).into(holder.binding.imageView2)
        holder.binding.textView.text = data.productTitle
        holder.binding.textView3.text = data.productCategory
        holder.binding.textView4.text = data.productAddress

        holder.binding.button.text = data.productPrice
    }

    override fun getItemCount(): Int {
        return list.size
    }


}