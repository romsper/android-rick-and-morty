package com.romsper.firebase_authentication.ui.main.adapter

import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.romsper.firebase_authentication.R
import com.romsper.firebase_authentication.databinding.RecyclerContactsItemBinding
import com.romsper.firebase_authentication.model.CharactersResponse
import com.romsper.firebase_authentication.model.Result
import java.util.Collections.addAll

class ContactsAdapter(private val items: ArrayList<Result>) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerContactsItemBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(private val binding: RecyclerContactsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Result) {
            binding.contactName.text = item.name
            Glide.with(binding.contactAvatar.context)
                .load(item.image)
                .transition(withCrossFade())
                .apply(RequestOptions().transform(RoundedCorners(50)))
                .into(binding.contactAvatar)
        }
    }

    fun addContacts(contacts: List<Result>) {
        this.items.apply {
            clear()
            addAll(contacts)
        }

    }
}