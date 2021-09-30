package com.romsper.firebase_authentication.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.romsper.firebase_authentication.R
import com.romsper.firebase_authentication.databinding.RecyclerFavoritesItemBinding
import com.romsper.firebase_authentication.model.SingleCharacterResponse


class FavoritesAdapter(private val items: List<SingleCharacterResponse>) : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerFavoritesItemBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(private val binding: RecyclerFavoritesItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SingleCharacterResponse) {
            Glide.with(binding.favoritesAvatar.context)
                .load(item.image)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.favoritesAvatar);
        }
    }
}