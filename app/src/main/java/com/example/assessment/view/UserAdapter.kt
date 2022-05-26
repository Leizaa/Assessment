package com.example.assessment.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.assessment.databinding.UserItemViewBinding
import com.example.assessment.model.User

class UserAdapter(
    private val onClick: (user: User) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private val data = ArrayList<User>()

    fun setItems(isNewSearch: Boolean, items: ArrayList<User>) {
        if (isNewSearch) {
            val prevSize = this.data.size
            this.data.clear()
            notifyItemRangeRemoved(0, prevSize)
        }
        val startIndex = if (data.size == 0) 0 else data.size - 1
        this.data.addAll(items)
        notifyItemRangeChanged(startIndex, items.size)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            UserItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class UserViewHolder(
        private val binding: UserItemViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(data: User) {
            with(binding) {
                usernameTextView.text = "@${data.username}"
                Glide.with(root)
                    .load(data.imageUrl)
                    .transform(CircleCrop())
                    .into(userProfileImage)
                root.setOnClickListener { onClick(data) }
            }
        }
    }
}