package ru.dayone.main.my_groups.data.network.models

import com.google.gson.annotations.SerializedName

data class GroupMember(
    @SerializedName("id") val id: String,
    @SerializedName("memberId") val memberId: String,
    @SerializedName("groupId") val groupId: String,
    @SerializedName("score") val score: Int
)
