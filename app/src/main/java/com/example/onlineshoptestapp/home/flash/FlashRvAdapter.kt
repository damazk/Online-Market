package com.example.onlineshoptestapp.home.flash

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshoptestapp.R
import com.example.onlineshoptestapp.domain.model.Good
import com.squareup.picasso.Picasso

class FlashRvAdapter(val flashItems: List<Good>) : RecyclerView.Adapter<FlashRvAdapter.FlashItemViewHolder>() {

    class FlashItemViewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashItemViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.flash_item, parent, false)
        return FlashItemViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: FlashItemViewHolder, position: Int) {
        val category = holder.itemView.findViewById<TextView>(R.id.categoryTv)
        val productName = holder.itemView.findViewById<TextView>(R.id.productNameTv)
        val price = holder.itemView.findViewById<TextView>(R.id.priceTv)
        val discount = holder.itemView.findViewById<TextView>(R.id.discountTv)
        val productImage = holder.itemView.findViewById<ImageView>(R.id.productImg)
        category.text = flashItems[position].category
        productName.text = flashItems[position].name
        price.text = "$ " + flashItems[position].price.toString()
        discount.text = flashItems[position].discount.toString() + "% off"
        Picasso.get().load(flashItems[position].imageUrl?.toUri()).into(productImage)
    }

    override fun getItemCount() = flashItems.size
}