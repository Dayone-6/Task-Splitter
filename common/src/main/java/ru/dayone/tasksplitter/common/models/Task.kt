package ru.dayone.tasksplitter.common.models

import com.google.gson.annotations.SerializedName

data class Task(
    @SerializedName("id") val id: String,
    @SerializedName("groupId") val groupId: String,
    @SerializedName("name") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("winner") val winner: String,
    @SerializedName("status") val status: Int
)