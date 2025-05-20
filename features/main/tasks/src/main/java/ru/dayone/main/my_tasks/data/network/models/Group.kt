package ru.dayone.main.my_tasks.data.network.models

import com.google.gson.annotations.SerializedName

data class Group(
    @SerializedName("creatorId") val creatorId: String
)