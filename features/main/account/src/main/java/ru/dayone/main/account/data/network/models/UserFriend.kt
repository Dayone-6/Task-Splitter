package ru.dayone.main.account.data.network.models

import com.google.gson.annotations.SerializedName

data class UserFriend (
    @SerializedName("userId") val userId: String,
    @SerializedName("friend") val friendId: String
)