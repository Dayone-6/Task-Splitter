package ru.dayone.main.account.domain.datasource

import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.utils.Result

interface AccountLocalDataSource {
    suspend fun getUser(): User?
    suspend fun deleteUser(): Result<Unit>
}