package ru.dayone.auth.domain.datasource

import ru.dayone.auth.domain.model.User
import ru.dayone.tasksplitter.common.utils.Result

interface AuthLocalDataSource {
    suspend fun saveCurrentUser(user: User)

    suspend fun loadCurrentUser(): Result<User>
}