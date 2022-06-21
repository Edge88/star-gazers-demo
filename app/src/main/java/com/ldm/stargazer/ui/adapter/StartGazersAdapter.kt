package com.ldm.stargazer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ldm.stargazer.api.response.StarGazersUser
import com.ldm.stargazer.databinding.StargItemBinding

class StartGazersAdapter: PagingDataAdapter<StarGazersUser, StartGazersAdapter.UserStarViewHolder>(USER_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserStarViewHolder {
        val binding =
            StargItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return UserStarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserStarViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class UserStarViewHolder(private val binding: StargItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: StarGazersUser) {

            binding.apply {
                Glide.with(itemView)
                    .load(user.avatarUrl)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(avatar)

                tvUserName.text = user.login
            }
        }
    }

    companion object {
        private val USER_COMPARATOR = object : DiffUtil.ItemCallback<StarGazersUser>() {
            override fun areItemsTheSame(oldItem: StarGazersUser, newItem: StarGazersUser) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: StarGazersUser, newItem: StarGazersUser) =
                oldItem == newItem
        }
    }

}