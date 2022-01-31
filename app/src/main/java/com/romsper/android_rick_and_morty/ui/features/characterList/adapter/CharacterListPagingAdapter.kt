package com.romsper.android_rick_and_morty.ui.features.characterList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.romsper.android_rick_and_morty.databinding.RecyclerCharacterItemBinding
import com.romsper.android_rick_and_morty.models.Result

class CharacterListPagingAdapter(private val characterListItemClickListener: CharacterListItemClickListener) :
    PagingDataAdapter<Result, CharacterListPagingAdapter.ViewHolder>(ContactsDiffCallBack()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterListPagingAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerCharacterItemBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterListPagingAdapter.ViewHolder, position: Int) {
        val data = getItem(position)!!
        holder.itemView.setOnClickListener {
            characterListItemClickListener.onCharacterListItemClickListener(data)
        }
        return holder.bind(getItem(position)!!)
    }

    inner class ViewHolder(private val binding: RecyclerCharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Result) {
            binding.contactName.text = item.name
            Glide.with(binding.contactAvatar.context)
                .load(item.image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions().transform(RoundedCorners(50)))
                .into(binding.contactAvatar)
        }
    }
}

class ContactsDiffCallBack : DiffUtil.ItemCallback<Result>() {
    override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem == newItem
    }
}