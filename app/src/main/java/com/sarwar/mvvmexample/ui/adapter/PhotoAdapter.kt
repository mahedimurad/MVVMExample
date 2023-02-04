package com.sarwar.mvvmexample.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.sarwar.mvvmexample.R
import com.sarwar.mvvmexample.data.network.model.ImageModel

class PhotoAdapter(val context: Context, val imageModels: ArrayList<ImageModel>, val onItemClick: (((ImageModel) -> Unit))) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    class PhotoViewHolder(view: View) : ViewHolder(view){
        val ivImage = itemView.findViewById<ImageView>(R.id.ivImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image,parent,false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val imageModel = imageModels[position]

        //holder.ivImage.setImageResource(R.drawable.ic_image_placeholder)
        Glide.with(context)
            .load(imageModel.urls.thumb)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.ivImage)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(imageModels[position])
        }
    }

    override fun getItemCount(): Int {
        return imageModels.size
    }
}