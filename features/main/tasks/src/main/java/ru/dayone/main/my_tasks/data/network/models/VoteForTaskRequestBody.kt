package ru.dayone.main.my_tasks.data.network.models

import com.google.gson.annotations.SerializedName

data class VoteForTaskRequestBody(
    @SerializedName("userId") val userId: String,
    @SerializedName("vote") val vote: Int
)