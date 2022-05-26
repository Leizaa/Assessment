package com.example.assessment.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @SerializedName("avatar_url")
    val imageUrl: String? = "",
    @SerializedName("login")
    val username: String? = ""
) : Parcelable
