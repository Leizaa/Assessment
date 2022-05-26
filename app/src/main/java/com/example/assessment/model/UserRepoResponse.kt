package com.example.assessment.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserRepoResponse(
    @SerializedName("name")
    val repositoryName: String? = "",
    val description: String? = "",
    @SerializedName("stargazers_count")
    val stargazersCount: Int? = 0,
    @SerializedName("owner")
    val user: User? = User(),
    @SerializedName("updated_at")
    val updatedDate: String? = "",
) : Parcelable
