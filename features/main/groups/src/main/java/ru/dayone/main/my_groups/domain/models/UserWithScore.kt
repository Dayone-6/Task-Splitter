package ru.dayone.main.my_groups.domain.models

data class UserWithScore(
    val id: String,
    val name: String,
    val nickname: String,
    val score: Int,
    val color: Int
)