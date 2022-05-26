package com.example.assessment.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.assessment.R
import com.example.assessment.databinding.RepositoryItemViewBinding
import com.example.assessment.model.UserRepoResponse
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class RepositoryAdapter : RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    private val data = ArrayList<UserRepoResponse>()

    fun setItems(items: ArrayList<UserRepoResponse>) {
        val startIndex = if (data.size == 0) 0 else data.size - 1
        this.data.addAll(items)
        notifyItemRangeChanged(startIndex, items.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder(
            RepositoryItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            parent.context
        )
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RepositoryViewHolder(
        private val binding: RepositoryItemViewBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: UserRepoResponse) {
            with(binding) {
                repositoryNameTextView.text = data.repositoryName
                repositoryDescriptionTextView.text = data.description
                repositoryDescriptionTextView.isVisible = data.description?.isNotBlank() ?: false
                repositoryStarTextView.text = data.stargazersCount.toString()
                repositoryUpdateDateTextView.text = context.getString(
                    R.string.update_date,
                    getUpdatedDateHour(data.updatedDate, context)
                )
                Glide.with(root)
                    .load(data.user?.imageUrl)
                    .transform(CircleCrop())
                    .into(profileImage)
            }
        }

        private fun getUpdatedDateHour(date: String?, context: Context): String {
            val dateFormat = "yyyy-MM-dd'T'hh:mm:SS'Z'"

            date?.let {
                try {
                    val sdf = SimpleDateFormat(dateFormat, Locale.getDefault())
                    val updatedDate = sdf.parse(it) ?: Date()
                    val currentDate = Date()
                    val diff = currentDate.time - updatedDate.time
                    val diffInMin = TimeUnit.MILLISECONDS.toMinutes(diff)
                    val diffInHours = TimeUnit.MILLISECONDS.toHours(diff)
                    val diffInDays = TimeUnit.MILLISECONDS.toDays(diff)
                    val diffInMonth = diffInDays / 30
                    val diffInYear = diffInMonth / 30

                    when {
                        diffInYear > 0 -> {
                            val yearLabel = if (diffInYear > 1) context.getString(R.string.years)
                            else context.getString(R.string.years).dropLast(1)
                            return "$diffInYear $yearLabel"
                        }
                        diffInMonth > 0 -> {
                            val monthLabel = if (diffInMonth > 1) context.getString(R.string.months)
                            else context.getString(R.string.months).dropLast(1)
                            return "$diffInMonth $monthLabel"
                        }
                        diffInDays > 0 -> {
                            val daysLabel = if (diffInDays > 1) context.getString(R.string.days)
                            else context.getString(R.string.days).dropLast(1)
                            return "$diffInDays $daysLabel"
                        }
                        diffInHours > 0 -> {
                            val hoursLabel = if (diffInHours > 1) context.getString(R.string.hours)
                            else context.getString(R.string.hours).dropLast(1)
                            return "$diffInHours $hoursLabel"
                        }
                        diffInMin >= 0 -> {
                            val minutesLabel = if (diffInMin > 1) context.getString(R.string.minutes)
                            else context.getString(R.string.minutes).dropLast(1)
                            return "$diffInMin $minutesLabel"
                        }
                        else -> return@let
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    return ""
                }
            }
            return ""
        }
    }
}