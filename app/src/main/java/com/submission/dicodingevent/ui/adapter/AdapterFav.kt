package com.submission.dicodingevent.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.submission.dicodingevent.data.respon.ListEventsItem
import com.submission.dicodingevent.databinding.LsfavBinding

class AdapterFav(
    private val onItemClick: (ListEventsItem) -> Unit
) : ListAdapter<ListEventsItem, AdapterFav.FavoriteViewHolder>(FavoriteDiffCallback()) {

    inner class FavoriteViewHolder(private val binding: LsfavBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favoriteItem: ListEventsItem) = with(binding) {
            favtittle.text = favoriteItem.name
            Glide.with(root.context)
                .load(favoriteItem.mediaCover)
                .into(favimg)

            root.setOnClickListener { onItemClick(favoriteItem) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = LsfavBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class FavoriteDiffCallback : DiffUtil.ItemCallback<ListEventsItem>() {
    override fun areItemsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
        return oldItem == newItem
    }
}
