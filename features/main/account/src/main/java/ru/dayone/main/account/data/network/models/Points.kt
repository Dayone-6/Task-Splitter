package ru.dayone.main.account.data.network.models

import com.google.gson.annotations.SerializedName

data class Points(
    @SerializedName("points") val points: Int
)