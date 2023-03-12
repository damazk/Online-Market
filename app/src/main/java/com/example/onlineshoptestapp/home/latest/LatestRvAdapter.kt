package com.example.onlineshoptestapp.home.latest

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toFile
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshoptestapp.R
import com.example.onlineshoptestapp.domain.model.Good
import com.squareup.picasso.Picasso

class LatestRvAdapter(val latestItems: List<Good>) : RecyclerView.Adapter<LatestRvAdapter.LatestItemViewHolder>() {

    class LatestItemViewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestItemViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.latest_item, parent, false)
        return LatestItemViewHolder(viewHolder)
    }

    override fun getItemCount() = latestItems.size

    override fun onBindViewHolder(holder: LatestItemViewHolder, position: Int) {
        val category = holder.itemView.findViewById<TextView>(R.id.categoryTv)
        val productName = holder.itemView.findViewById<TextView>(R.id.productNameTv)
        val price = holder.itemView.findViewById<TextView>(R.id.priceTv)
        val productImage = holder.itemView.findViewById<ImageView>(R.id.productImg)
        category.text = latestItems[position].category
        productName.text = latestItems[position].name
        price.text = "$ " + latestItems[position].price
        Picasso.get().load(latestItems[position].imageUrl?.toUri()).into(productImage)
    }
}