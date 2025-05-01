package ru.dayone.main.my_groups.data.network.models

import com.google.gson.annotations.SerializedName

data class Task(
    @SerializedName("id") val id: String,
    @SerializedName("groupId") val groupId: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("winner") val winner: String,
    @SerializedName("status") val status: Int
)
