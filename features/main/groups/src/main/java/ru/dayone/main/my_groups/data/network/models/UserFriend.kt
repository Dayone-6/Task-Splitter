package ru.dayone.main.my_groups.data.network.models

import com.google.gson.annotations.SerializedName

data class UserFriend (
    @SerializedName("userId") val userId: String,
    @SerializedName("friend") val friendId: String
)
