package ru.dayone.main.my_groups.data.network.models

import com.google.gson.annotations.SerializedName

data class AddMemberToGroupRequestBody(
    @SerializedName("memberId") val id: String
)
