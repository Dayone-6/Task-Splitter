package ru.dayone.main.account.data.network.models.friends

import com.google.gson.annotations.SerializedName

data class UserFriend (
    @SerializedName("userId") val userId: String,
    @SerializedName("friendId") val friendId: String
)