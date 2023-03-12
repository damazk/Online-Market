package com.example.onlineshoptestapp.home.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshoptestapp.R

class CarouselRvAdapter(val categoriesList: List<Category>) :
    RecyclerView.Adapter<CarouselRvAdapter.CarouselItemViewHolder>() {

    class CarouselItemViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselItemViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return CarouselItemViewHolder(viewHolder)
    }

    override fun getItemCount() = categoriesList.size

    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        val image = holder.itemView.findViewById<ImageView>(R.id.image)
        val title = holder.itemView.findViewById<TextView>(R.id.titleTv)
        image.setImageResource(categoriesList[position].image)
        title.text = categoriesList[position].title
    }
}