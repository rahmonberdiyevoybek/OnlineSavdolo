package com.example.onlinesavdo.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlinesavdo.R
import com.example.onlinesavdo.model.ProductModel
import com.example.onlinesavdo.utils.Constants.HOST_IMAGE
import com.google.android.gms.common.internal.Constants
import kotlinx.android.synthetic.main.cart_item_layout.view.*

class CartAdapter ( val items: List<ProductModel>):RecyclerView.Adapter<CartAdapter.ItemHolder>(){

    class ItemHolder(view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.cart_item_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
     val item = items[position]

        holder.itemView.tvPrice.text = item.price
        holder.itemView.tvName.text = item.name

        Glide.with(holder.itemView).load(com.example.onlinesavdo.utils.Constants.HOST_IMAGE + item.image).into(holder.itemView.imgProduct)
        holder.itemView.tvCount.text = item.cartCount.toString()
    }

    override fun getItemCount(): Int {
        return items.count()
    }
}