package com.indian.dhirajchauhanterasoltask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.indian.dhirajchauhanterasoltask.R
import com.indian.dhirajchauhanterasoltask.databinding.ItemMoviesBinding
import com.indian.dhirajchauhanterasoltask.model.MoviesItem


class MoviesAdapter(val context: Context) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    var onItemClick: ((MoviesItem) -> Unit)? = null

    private val differCallBack = object : DiffUtil.ItemCallback<MoviesItem>(){
        override fun areItemsTheSame(oldItem: MoviesItem, newItem: MoviesItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MoviesItem, newItem: MoviesItem): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.item_movies,
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var moviesItem:MoviesItem = differ.currentList[position]
        with(holder) {
            binding.title.text ="${ moviesItem.title} ${moviesItem.year}"
            Glide.with(context).load(moviesItem.info.image_url).into(binding.image)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = ItemMoviesBinding.bind(itemView)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(differ.currentList[position])
            }
        }
    }
}