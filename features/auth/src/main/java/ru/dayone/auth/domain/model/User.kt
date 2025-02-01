package ru.dayone.auth.domain.model

data class User(
    val id: String = "",
    val name: String? = null,
    val nickname: String? = null
)