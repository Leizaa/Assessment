package com.example.assessment.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetailResponse (
        @SerializedName("avatar_url")
        val imageUrl: String? = "",
        @SerializedName("name")
        val fullName: String? = "",
        @SerializedName("login")
        val username: String? = "",
        @SerializedName("bio")
        val bio: String? = "",
        @SerializedName("followers")
        val followersTotal: Int? = 0,
        @SerializedName("following")
        val followingTotal: Int? = 0,
        val location: String? = "",
        val email: String? = "",
) : Parcelable