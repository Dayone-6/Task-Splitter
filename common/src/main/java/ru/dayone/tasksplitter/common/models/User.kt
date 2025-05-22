package ru.dayone.tasksplitter.common.models

data class User(
    val id: String = "",
    val name: String? = null,
    val nickname: String? = null,
    val color: Int? = null,
    val points: Int? = null
)