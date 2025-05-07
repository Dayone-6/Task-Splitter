package ru.dayone.main.my_groups.data.network.models

import com.google.gson.annotations.SerializedName

data class CreateTaskRequestBody(
    @SerializedName("name") val title: String,
    @SerializedName("description") val description: String
)
