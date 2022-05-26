package com.example.assessment.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchResponse (
    @SerializedName("total_count")
    val totalCount: Int? = 0,
    @SerializedName("items")
    val userList: List<User>? = listOf()
) : Parcelable