package ru.dayone.main.my_groups.data.network.models

import com.google.gson.annotations.SerializedName

data class Group(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("creatorId") val creatorId: String,
    @SerializedName("members") val members: List<GroupMember>
)
