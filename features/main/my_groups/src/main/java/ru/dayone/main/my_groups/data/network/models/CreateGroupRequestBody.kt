package ru.dayone.main.my_groups.data.network.models

import com.google.gson.annotations.SerializedName

data class CreateGroupRequestBody(
    @SerializedName("creatorId") val creatorId: String,
    @SerializedName("name") val name: String
)