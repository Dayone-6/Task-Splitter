package ru.dayone.main.my_tasks.data.models

import ru.dayone.tasksplitter.common.models.User

data class VoteUI(
    val user: User,
    val score: Int
)
