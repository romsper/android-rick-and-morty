package com.romsper.firebase_authentication.ui.features.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.romsper.firebase_authentication.databinding.RecyclerFavoritesItemBinding
import com.romsper.firebase_authentication.util.FavoriteItem


class FavoritesAdapter(
    private val items: ArrayList<FavoriteItem>,
    private val favoritesItemClickListener: FavoritesItemClickListener
) : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerFavoritesItemBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items[position]
        holder.itemView.setOnClickListener {
            favoritesItemClickListener.onFavoritesItemClickListener(data)
        }
        return holder.bind(items[position])
    }

    inner class ViewHolder(private val binding: RecyclerFavoritesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FavoriteItem) {
            binding.btnRemoveFavorites.setOnClickListener {
                favoritesItemClickListener.onRemoveFavoritesItemClickListener(item)
            }
            Glide.with(binding.favoritesAvatar.context)
                .load(item.avatarUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions().transform(RoundedCorners(30)))
                .into(binding.favoritesAvatar)
            binding.favoritesName.text = item.name
        }
    }

    fun addFavorites(favorites: List<FavoriteItem>) {
        this.items.apply {
            clear()
            addAll(favorites)
        }
    }
}