package ru.dayone.main.my_tasks.data.network.models

import com.google.gson.annotations.SerializedName

data class Vote(
    @SerializedName("id") val id: String,
    @SerializedName("userId") val userId: String,
    @SerializedName("vote") val vote: Int
)
